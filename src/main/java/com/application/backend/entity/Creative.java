package com.application.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;


@Data
public class Creative {
    @TableField(exist = false)
    private Artworks lastSubmit;
    @TableField(exist = false)
    private Artworks lastView;
    @TableField(exist = false)
    private Favorite lastFavorite;
    @TableField(exist = false)
    private int fans;
    @TableField(exist = false)
    private int views;

    public Creative(Artworks lastSubmit, Artworks lastView, Favorite lastFavorite, int fans, int views) {
        this.lastSubmit = lastSubmit;
        this.lastView = lastView;
        this.lastFavorite = lastFavorite;
        this.fans = fans;
        this.views = views;
    }

    public Creative() {
        super();
    }

    public Artworks getLastSubmit() {
        return lastSubmit;
    }

    public void setLastSubmit(Artworks lastSubmit) {
        this.lastSubmit = lastSubmit;
    }

    public Artworks getLastView() {
        return lastView;
    }

    public void setLastView(Artworks lastView) {
        this.lastView = lastView;
    }

    public Favorite getLastFavorite() {
        return lastFavorite;
    }

    public void setLastFavorite(Favorite lastFavorite) {
        this.lastFavorite = lastFavorite;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
