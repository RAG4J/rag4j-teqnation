package org.rag4j.workshop;


import org.rag4j.rag.embedding.Embedder;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Component used to create an embedding for a piece of text. The embedding is a vector of numbers that represents the
 * text in a vector space. This embedder uses the alphabet as the vector space. Each letter in the alphabet is
 * represented by a number in the vector. The vector is as long as the alphabet and each letter is represented by a
 * number in the vector. The number is the number of times the letter appears in the text.
 */
public class AlphabetEmbedder implements Embedder {
    private static final Pattern DIACRITICS_AND_FRIENDS
            = Pattern.compile("[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+");

    private static String stripDiacritics(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = DIACRITICS_AND_FRIENDS.matcher(str).replaceAll("");
        return str;
    }

    /**
     * Creates an embedding for the provided text.
     * @param text the text to create an embedding for
     * @return the embedding as a list of doubles.
     */
    @Override
    public List<Float> embed(String text) {
        // TODO implement this method, use the test class to verify your implementation

        float[] counts = new float[26];
        // solution
        text = stripDiacritics(text.toLowerCase());

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                counts[c - 'a']++;
            }
        }
        // end of solution
        List<Float> list = new ArrayList<>();
        for (float value : counts) {
            list.add(value);
        }
        return list;
    }
}
