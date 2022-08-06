package ua.com.azbest.apptest3.model;

import java.util.Map;

public class WebContentFactory {

    public static WebContent getContent(Content content) {
        if (content.type.equals(ContentType.WEBPAGE)) {
            return new WebContent(((Map<String, String>)content.payload).get("url"));
        } else {
            throw new IllegalArgumentException("Illegal type");
        }
    }

}
