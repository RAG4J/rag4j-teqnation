package org.rag4j;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.rag4j.indexing.ContentReader;
import org.rag4j.indexing.InputDocument;
import org.rag4j.util.resource.JsonlReader;

public class TeqnationContentReader implements ContentReader {

    @Override
    public Stream<InputDocument> read() {
        Stream.Builder<InputDocument> builder = Stream.builder();

        JsonlReader jsonlReader = getJsonlReader();
        jsonlReader.getLines().forEach(talk -> {
            InputDocument.InputDocumentBuilder documentBuilder = InputDocument.builder()
                    .documentId(talk.get("title").toLowerCase().replace(" ", "-"))
                    .text(talk.get("description"))
                    .properties(new HashMap<>(talk));
            builder.add(documentBuilder.build());
        });

        return builder.build();
    }

    private JsonlReader getJsonlReader() {
        List<String> properties = List.of(
                "speakers",
                "title",
                "description",
                "room",
                "time",
                "tags"
        );
        return new JsonlReader(properties, "teqnation/sessions.jsonl");
    }
}
