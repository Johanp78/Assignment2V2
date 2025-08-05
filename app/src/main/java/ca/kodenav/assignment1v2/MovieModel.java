package ca.kodenav.assignment1v2;

import android.widget.ImageView;

import java.io.Serializable;

public class MovieModel implements Serializable {
    private String id;
    private String title;
    private String year;
    private String thumbnailUrl;
    private String userId;

    public MovieModel() {}

    public MovieModel(String id, String title, String year, String thumbnailUrl, String userId) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.thumbnailUrl = thumbnailUrl;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

