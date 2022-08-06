package ua.com.azbest.apptest3.model;

public enum ContentType {

    WEBPAGE("webpage"), TEXT("text");

    private String name;

    ContentType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
