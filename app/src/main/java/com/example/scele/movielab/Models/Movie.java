package com.example.scele.movielab.Models;

import java.io.Serializable;

public class Movie implements Serializable {

    String title;
    String primaryInfo;
    String ratings;
    String studio;
    String streamLink;
    Integer thumbnail;
    private int photoBack;

    public Movie(String title, Integer thumbnail, Integer photoBack) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.photoBack = photoBack;
    }

    public Movie(String title, Integer thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Movie(String title, String primaryInfo, String ratings, String studio, String streamLink, Integer thumbnail) {
        this.title = title;
        this.primaryInfo = primaryInfo;
        this.ratings = ratings;
        this.studio = studio;
        this.streamLink = streamLink;
        this.thumbnail = thumbnail;
    }

    public int getPhotoBack() {
        return photoBack;
    }

    public void setPhotoBack(int photoBack) {
        this.photoBack = photoBack;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrimaryInfo() {
        return primaryInfo;
    }

    public void setPrimaryInfo(String primaryInfo) {
        this.primaryInfo = primaryInfo;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getStreamLink() {
        return streamLink;
    }

    public void setStreamLink(String streamLink) {
        this.streamLink = streamLink;
    }

    public Integer getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Integer thumbnail) {
        this.thumbnail = thumbnail;
    }
}
