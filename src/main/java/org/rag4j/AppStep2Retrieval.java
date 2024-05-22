package org.rag4j;

import java.util.List;

import org.rag4j.indexing.InputDocument;
import org.rag4j.indexing.Splitter;
import org.rag4j.indexing.splitters.SentenceSplitter;
import org.rag4j.integrations.openai.OpenAIEmbedder;
import org.rag4j.integrations.openai.OpenAIFactory;
import org.rag4j.integrations.weaviate.WeaviateAccess;
import org.rag4j.integrations.weaviate.retrieval.WeaviateRetriever;
import org.rag4j.rag.embedding.Embedder;
import org.rag4j.rag.embedding.local.OnnxBertEmbedder;
import org.rag4j.rag.model.Chunk;
import org.rag4j.rag.retrieval.RetrievalOutput;
import org.rag4j.rag.retrieval.RetrievalStrategy;
import org.rag4j.rag.retrieval.Retriever;
import org.rag4j.rag.retrieval.strategies.TopNRetrievalStrategy;
import org.rag4j.rag.retrieval.strategies.WindowRetrievalStrategy;
import org.rag4j.rag.store.local.InternalContentStore;
import org.rag4j.util.keyloader.KeyLoader;

/**
 * The goal for step 2 is to understand how to retrieve content from a vector database. You used the retriever in
 *     step 1 to find matching chunks. In this step we take it a step further. In this step you learn about retrieval
 *     strategies. We use the retriever against a local vector database, and we use it to query Weaviate. Weaviate
 *     contains embeddings of all the sentences using the default OpenAIEmbedder.
 */
public class AppStep2Retrieval {

    private static final KeyLoader keyLoader = new KeyLoader();

    private static String source = "Ever thought about building your very own question-answering system? Like the one that powers Siri, " +
            "Alexa, or Google Assistant? Well, we've got something awesome lined up for you! In our hands-on " +
            "workshop, we'll guide you through the ins and outs of creating a question-answering system. You can use " +
            "Python or Java for the workshop. You'll get your hands dirty with vector stores and Large Language " +
            "Models, we help you combine these two in a " +
            "way you've never done before. You've probably used search engines for keyword-based searches, " +
            "right? Well, prepare to have your mind blown. We'll dive into something called semantic search, " +
            "which is the next big thing after traditional searches. It’s like moving from asking Google to search " +
            "\"best pizza places\" to \"Where can I find a pizza place that my gluten-intolerant, vegan friend " +
            "would love?\" you get the idea, right? We’ll be teaching you how to build an entire pipeline, " +
            "starting from collecting data from various sources, converting that into vectors (yeah, it’s more math, " +
            "but it’s cool, we promise), and storing it so you can use it to answer all sorts of queries. It's like " +
            "building your own mini Google! We've got a repository ready to help you set up everything you need on " +
            "your laptop. By the end of our workshop, you'll have your question-answering system ready and " +
            "running.So, why wait? Grab your laptop, bring your coding hat, and let's start building something " +
            "fantastic together.";

    public static void main(String[] args) {
        InputDocument inputDocument = InputDocument.builder()
                .documentId("jettro-daniel-teqnation-workshop-2022-03-30")
                .text(source)
                .build();
        Splitter splitter = new SentenceSplitter();
        Embedder embedder = new OnnxBertEmbedder();

        InternalContentStore contentStore = new InternalContentStore(embedder);
        contentStore.store(splitter.split(inputDocument));


        /****************************
         *** Retrieval strategies ***
         ****************************/

        RetrievalStrategy retrievalStrategy = new TopNRetrievalStrategy(contentStore);

        // TODO 1: Retrieve only the best matching chunk for the query "What is an alternative to keyword search?"
        //  Can you answer the question based on the retrieved chunk?
        //  (Use the retrieve method of RetrievalStrategy with 'maxResults' property 1)
        // BEGIN SOLUTION

        // END

        // TODO 2: Now use the WindowRetrievalStrategy, what size of the window would you use to answer the question?
        // BEGIN SOLUTION

        // END


        /**************************************
         *** Retrieve content from Weaviate ***
         **************************************/

        WeaviateAccess weaviateAccess = new WeaviateAccess(keyLoader);
        embedder = new OpenAIEmbedder(OpenAIFactory.obtainsClient(keyLoader.getOpenAIKey()));
        Retriever retriever = new WeaviateRetriever(weaviateAccess, embedder, false, List.of("title", "time", "room", "speakers", "tags"));

        // TODO 3: Retrieve the first chunk of the document with the id "using-ai-to-save-time-and-sanity"
        //  How many chunks does the document have?
        // BEGIN SOLUTION

        // END

        // TODO 4: Retrieve the four best matching chunks for the query "Wie heeft het over zoek technologie?".
        //  Use the window retrieval strategy with a window size of 2.
        //  Can you answer the question from the retrieved chunks?
        // BEGIN SOLUTION

        // END
    }
}
