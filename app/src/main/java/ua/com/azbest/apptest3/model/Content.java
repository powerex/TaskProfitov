package ua.com.azbest.apptest3.model;

public class Content {

    ContentType type;

    Object payload;

    public Content() {
    }

    public Content(String type, Object payload) {
        this.type = ContentType.valueOf(type.toUpperCase());
        this.payload = payload;
    }

    public ContentType getType() {
        return type;
    }

    public void setType(String type) {
        this.type = ContentType.valueOf(type.toUpperCase());
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
