package ua.com.azbest.apptest3.model;

import java.util.Map;

public class TextContentFactory {

    public static TextContent getContent(Content content) {
        if (content.type.equals(ContentType.TEXT)) {
            return new TextContent(((Map<String, String>)content.payload).get("text"));
        } else {
            throw new IllegalArgumentException("Illegal type");
        }
    }

}
