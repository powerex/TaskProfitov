package ua.com.azbest.apptest3.model;

public class TextContent {

    String text;

    public TextContent(String text) {
        this.text = text;
    }

    public TextContent() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
