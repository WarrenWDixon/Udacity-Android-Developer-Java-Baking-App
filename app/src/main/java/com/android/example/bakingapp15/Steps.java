package com.android.example.bakingapp15;

public class Steps {
    public Integer id;
    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnailURL;

    Steps(Integer id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }
}
