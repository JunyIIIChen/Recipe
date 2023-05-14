package com.example.recipeass2.user;

import android.media.Image;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_recipe_table")
public class FavoriteRecipe {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "saved_recipe_id")
    private int id;

    @ColumnInfo(name = "saved_recipe_name")
    private String name;

    @ColumnInfo(name = "saved_recipe_type")
    private String type;


    @ColumnInfo(name = "like_timestamp")
    private long likeTimestamp;

    // Empty constructor required for Firebase
    public FavoriteRecipe() {}
    // Constructor, getters and setters
    public FavoriteRecipe(String name, String type, long likeTimestamp) {
        this.name = name;
        this.type = type;
        this.likeTimestamp = likeTimestamp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getLikeTimestamp() {
        return likeTimestamp;
    }

    public void setLikeTimestamp(long likeTimestamp) {
        this.likeTimestamp = likeTimestamp;
    }
}
