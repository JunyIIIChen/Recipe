package com.example.recipeass2.search;

public class Recipe {
    private String title;
    private int id;
    private String type;
    private String imageUrl = "https://spoonacular.com/recipeImages/"+id+"-"+"240x150."+type;

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
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
