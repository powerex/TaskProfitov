package ua.com.azbest.apptest3.model;

import java.net.URI;
import java.net.URISyntaxException;

public class WebContent {

    URI uri = null;

    public WebContent(String uri) {
        try {
            this.uri = new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public WebContent(URI url) {
    }

    public URI getUrl() {
        return uri;
    }

    public void setUrl(URI uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return this.uri.toString();
    }
}
