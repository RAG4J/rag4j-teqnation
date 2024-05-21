package org.rag4j;

import java.util.ArrayList;
import java.util.List;

import org.rag4j.indexing.InputDocument;
import org.rag4j.indexing.Splitter;
import org.rag4j.indexing.splitters.SentenceSplitter;
import org.rag4j.rag.embedding.Embedder;
import org.rag4j.rag.model.Chunk;
import org.rag4j.rag.model.RelevantChunk;
import org.rag4j.rag.store.local.InternalContentStore;
import org.rag4j.util.keyloader.KeyLoader;
import org.rag4j.workshop.AlphabetEmbedder;

/**
 * The goal for step 1 is to understand the required elements for ingesting content into a vector database:
 *     - 1. Read the content from a source
 *     - 2. Split the content into chunks using a splitter.
 *     - 3. Create a vector or embedding from the text using an embedder.
 *     - 4. Store the embedding in a content/vector store.
 *
 *     The size of the chunks you create by the splitter has a big impact on the precision of the system. If
 *     the chunks are too small, the system will have a hard time to find the correct answer. If the chunks are too
 *     big, the system will have a hard time to find the correct answer. The goal is to find a good balance between
 *     the two.
 *
 *     The embedders are essential to the similarity search of the vector store. You will use a weird embedder
 *     called the AlphabetEmbedder. This embedder will create an embedding for a chunk of text based on the alphabet.
 *     A better, still local running, embedder is the OnnxEmbedder. The best embedder you use is the OpenAIEmbedder.
 */
public class AppStep1Ingestion {

    private static KeyLoader keyLoader = new KeyLoader();

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

        /*****************************
         *** Splitting the content ***
         *****************************/
        Splitter splitter;
        List<Chunk> chunks = new ArrayList<>();

        // TODO 1: Use the sentence splitter to split content into chunks.
        // BEGIN SOLUTION

        // END

        System.out.println("Number of chunks: " + chunks.size());

        // TODO 2: Print the text of the chunks to verify the sentences. We think 17 would be better.
        //  Spot the problem in the content that causes the splitter to create 16 chunks.
        // BEGIN SOLUTION

        // END


        /**********************************
         *** Storing content embeddings ***
         **********************************/
        Embedder embedder = new AlphabetEmbedder();
        List<Float> embedding = embedder.embed(chunks.get(0).getText());
        // TODO 3: Look at the embedding. What is the length of the embedding? How does the embedding work?
        // BEGIN SOLUTION

        // END

        InternalContentStore contentStore = new InternalContentStore(embedder);
        contentStore.store(chunks);
        List<RelevantChunk> relevantChunks = contentStore.findRelevantChunks("What is the future after traditional searches?", 5);

        // TODO 4: Print the properties of the relevant chunks. What is your opinion on the results?
        //  Did you take a look at the scores?
        // BEGIN SOLUTION

        // END

        // TODO 5: Now try replacing the AlphabetEmbedder with the OnnxBertEmbedder.
        //  Wat is the size of the embedding? Do the results improve?

        // TODO 6: Now change the embedder to the OpenAIEmbedder.
        //  (You can create an OpenAIClient by using "OpenAIFactory.obtainsClient(keyLoader.getOpenAIKey())".)
        //  Wat is the size of the embedding? Do the results improve?

        // TODO 7: Replace the splitter by a MaxSizeSplitter.
        //  What is the number of chunks now? What is the impact on the results?
    }
}
