package ua.com.azbest.apptest3.model;

public class ListItem {

    private Long id;

    private String title;

    public ListItem(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
