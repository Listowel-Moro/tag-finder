package com.listo.tag_finder.dto;

public class ImageDTO {
    private String url;
    private String key;

    public ImageDTO(String url, String key) {
        this.url = url;
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public String getKey() {
        return key;
    }
}
