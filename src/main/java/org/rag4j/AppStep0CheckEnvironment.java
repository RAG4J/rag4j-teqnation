package org.rag4j;

import org.rag4j.util.keyloader.KeyLoader;

public class AppStep0CheckEnvironment {
    public static void main(String[] args) {
        KeyLoader keyLoader = new KeyLoader();

        String secretKey = keyLoader.getOpenAIKey();

        if (secretKey == null || secretKey.isEmpty()) {
            System.out.println("OpenAI key not found in environment variables.");
        } else {
            System.out.println("OpenAI key found in environment variables.");
        }
    }
}
