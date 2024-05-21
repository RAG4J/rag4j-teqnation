package org.rag4j;

import java.util.List;

import org.rag4j.integrations.openai.OpenAIChatService;
import org.rag4j.integrations.openai.OpenAIEmbedder;
import org.rag4j.integrations.openai.OpenAIFactory;
import org.rag4j.integrations.weaviate.WeaviateAccess;
import org.rag4j.integrations.weaviate.retrieval.WeaviateRetriever;
import org.rag4j.rag.embedding.Embedder;
import org.rag4j.rag.generation.AnswerGenerator;
import org.rag4j.rag.generation.chat.ChatService;
import org.rag4j.rag.retrieval.RetrievalOutput;
import org.rag4j.rag.retrieval.RetrievalStrategy;
import org.rag4j.rag.retrieval.Retriever;
import org.rag4j.rag.retrieval.strategies.WindowRetrievalStrategy;
import org.rag4j.util.keyloader.KeyLoader;

/**
 * The goal for this step is to understand how it works to create an answer to a question given a context. The
 *     OpenAIAnswerGenerator is used to generate an answer. The OpenAIClient is used to communicate with the
 *     OpenAI API. The KeyLoader is used to load the OpenAI key from the environment variables.
 */
public class AppStep3Generation {

    private static final KeyLoader keyLoader = new KeyLoader();

    public static void main(String[] args) {
        ChatService chatService = new OpenAIChatService(OpenAIFactory.obtainsClient(keyLoader.getOpenAIKey()));
        AnswerGenerator answerGenerator = new AnswerGenerator(chatService);

        String question = "What elements does a pipeline for q and a systems need?";
        String context = "We’ll be teaching you how to build an entire pipeline, starting from collecting data from various " +
        "sources, converting that into vectors (yeah, it’s more math, but it’s cool, we promise), and storing " +
        "it so you can use it to answer all sorts of queries.";


        /**************************
         *** Generate an answer ***
         **************************/

        // TODO 1: Generate an answer using the answerGenerator. Use the question and context variables.
        //  Does the result answer the question?
        // BEGIN SOLUTION

        // END



        /**************************************
         *** Create the complete Q&A system ***
         **************************************/

        WeaviateAccess weaviateAccess = new WeaviateAccess(keyLoader);
        Embedder embedder = new OpenAIEmbedder(OpenAIFactory.obtainsClient(keyLoader.getOpenAIKey()));
        Retriever retriever = new WeaviateRetriever(weaviateAccess, embedder, false, List.of("title", "time", "room", "speakers", "tags"));
        question = "What is an alternative to keyword search?";

        RetrievalStrategy retrievalStrategy = new WindowRetrievalStrategy(retriever, 1);
        RetrievalOutput output = retrievalStrategy.retrieve(question, 1);
        context = output.constructContext();

        // TODO 2: Generate an answer using the answerGenerator, the obtained context and the question.
        //  Did it find an answer? If not, why not? What can you do?
        // BEGIN SOLUTION

        // END

        // TODO 3: Play around with the different components, ask other questions, choose other strategy, etc
        // BEGIN SOLUTION

        // END
    }
}
