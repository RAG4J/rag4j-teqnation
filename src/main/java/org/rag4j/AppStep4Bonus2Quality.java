package org.rag4j;

import java.util.List;

import org.rag4j.integrations.openai.OpenAIChatService;
import org.rag4j.integrations.openai.OpenAIEmbedder;
import org.rag4j.integrations.openai.OpenAIFactory;
import org.rag4j.integrations.weaviate.WeaviateAccess;
import org.rag4j.integrations.weaviate.retrieval.WeaviateRetriever;
import org.rag4j.rag.embedding.Embedder;
import org.rag4j.rag.generation.ObservedAnswerGenerator;
import org.rag4j.rag.generation.chat.ChatService;
import org.rag4j.rag.generation.quality.AnswerFromContextQuality;
import org.rag4j.rag.generation.quality.AnswerQualityService;
import org.rag4j.rag.generation.quality.AnswerToQuestionQuality;
import org.rag4j.rag.retrieval.RetrievalStrategy;
import org.rag4j.rag.retrieval.Retriever;
import org.rag4j.rag.retrieval.strategies.DocumentRetrievalStrategy;
import org.rag4j.rag.tracker.RAGObserver;
import org.rag4j.rag.tracker.RAGTracker;
import org.rag4j.util.keyloader.KeyLoader;

/**
 * This is a bonus step that shows how to determine the quality of your RAG system. The quality is depending on
 *     the quality of all components. First we have the retriever that finds the relevant items for the question. The
 *     retriever strategy constructs the context. The generator is responsible for answering the question. If the generated
 *     answer is no answer to the question, the quality is low. If the generator creates a good answer to the question, we
 *     still need to verify if the answer is related to the context. If the answer is not related to the context, the
 *     quality is low. Therefore, we have three quality metrics:
 *     - Retrieval quality
 *     - Answer to question quality
 *     - Answer from context quality
 *
 *     In this step you explore all three quality metrics.
 */
public class AppStep4Bonus2Quality {

    private static final KeyLoader keyLoader = new KeyLoader();

    public static void main(String[] args) {
        /****************************************************************
         *** Answer to Question quality + Answer from Context quality ***
         ****************************************************************/

//        To determine the quality of the answer, you use the LLM model from OpenAI. We prompt the LLM to rate the answer
//        with a number between 1 and 5, where 1 is the worst and 5 is the best. At first we present the question and the
//        answer.

        String question = "What session does this text describe?";
        String context = "Ever thought about building your very own question-answering system? Like the one that powers Siri, " +
        "Alexa, or Google Assistant? Well, we've got something awesome lined up for you! In our hands-on " +
        "workshop, we'll guide you through the ins and outs of creating a question-answering system. You can " +
        "use Python or Java for the workshop. You'll get your hands dirty with vector stores and Large Language " +
        "Models, we help you combine these two in a " +
        "way you've never done before. You've probably used search engines for keyword-based searches, " +
        "right? Well, prepare to have your mind blown. We'll dive into something called semantic search, " +
        "which is the next big thing after traditional searches. It’s like moving from asking Google to search " +
        "\"best pizza places\" to \"Where can I find a pizza place that my gluten-intolerant, vegan friend " +
        "would love?\" you get the idea, right? We’ll be teaching you how to build an entire pipeline, " +
        "starting from collecting data from various sources, converting that into vectors (yeah, it’s more math," +
        " but it’s cool, we promise), and storing it so you can use it to answer all sorts of queries. It's like" +
        " building your own mini Google! We've got a repository ready to help you set up everything you need on " +
        "your laptop. By the end of our workshop, you'll have your question-answering system ready and " +
        "running.So, why wait? Grab your laptop, bring your coding hat, and let's start building something " +
        "fantastic together.";


        ChatService chatService = new OpenAIChatService(OpenAIFactory.obtainsClient(keyLoader.getOpenAIKey()));
        ObservedAnswerGenerator answerGenerator = new ObservedAnswerGenerator(chatService);
        String answer = answerGenerator.generateAnswer(question, context);

        RAGObserver observer = RAGTracker.getRAGObserver();
        System.out.printf("Question: %s%n", observer.getQuestion());
        System.out.printf("Context: %s%n", observer.getContext());
        System.out.printf("Answer: %s%n", answer);

        AnswerQualityService answerQuality = new AnswerQualityService(chatService);
        AnswerToQuestionQuality answerToQuestionQuality = answerQuality.determineQualityOfAnswerToQuestion(observer);
        System.out.printf("Answer to Question Quality: %s - Reason: %s%n", answerToQuestionQuality.getQuality(), answerToQuestionQuality.getReason());
        AnswerFromContextQuality answerFromContextQuality = answerQuality.determineQualityAnswerFromContext(observer);
        System.out.printf("Answer from Context Quality: %s - Reason: %s%n", answerFromContextQuality.getQuality(), answerFromContextQuality.getReason());

        RAGTracker.cleanup();

        // TODO 1: Play around with the context and the question.


        // TODO 2: If you feel adventurous, try to create the full application where the context is obtained through a
        //  retrieval strategy and the answer is generated by the generator. Then determine the quality of the answer.

        Embedder embedder = new OpenAIEmbedder(OpenAIFactory.obtainsClient(keyLoader.getOpenAIKey()));
        WeaviateAccess weaviateAccess = new WeaviateAccess(keyLoader);
        Retriever weaviateRetriever = new WeaviateRetriever(weaviateAccess, embedder, false, List.of("title", "time", "room", "speakers", "tags"));

        question = "Who are the people presenting the workshop about a q&a system?";
        // BEGIN SOLUTION
        RetrievalStrategy retrievalStrategy = new DocumentRetrievalStrategy(weaviateRetriever);
        context = retrievalStrategy.retrieve(question, 1).constructContext();
        answer = answerGenerator.generateAnswer(question, context);

        System.out.printf("Question: %s%n", question);
        System.out.printf("Context: %s%n", context);
        System.out.printf("Answer: %s%n", answer);

        answerToQuestionQuality = answerQuality.determineQualityOfAnswerToQuestion(observer);
        System.out.printf("Answer to Question Quality: %s - Reason: %s%n", answerToQuestionQuality.getQuality(), answerToQuestionQuality.getReason());
        answerFromContextQuality = answerQuality.determineQualityAnswerFromContext(observer);
        System.out.printf("Answer from Context Quality: %s - Reason: %s%n", answerFromContextQuality.getQuality(), answerFromContextQuality.getReason());

        RAGTracker.cleanup();
        // END
    }
}
