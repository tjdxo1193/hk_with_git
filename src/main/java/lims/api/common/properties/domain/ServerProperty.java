package lims.api.common.properties.domain;

public class ServerProperty {
    private final String url;

    public ServerProperty(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}