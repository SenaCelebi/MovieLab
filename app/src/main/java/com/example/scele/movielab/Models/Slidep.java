package com.example.scele.movielab.Models;

public class Slidep {

    private String image;
    private  String title;
    private int id;

    public Slidep(String image, String title, int id) {
        this.image = image;
        this.title = title;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
