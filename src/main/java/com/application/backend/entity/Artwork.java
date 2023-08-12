package com.application.backend.entity;

import org.springframework.web.multipart.MultipartFile;

public class Artwork {
    String username;
    MultipartFile picture;
    String subtitle;
    String introduce;

    public Artwork(String username, MultipartFile picture, String subtitle, String introduce) {
        this.username = username;
        this.picture = picture;
        this.subtitle = subtitle;
        this.introduce = introduce;
    }

    @Override
    public String toString() {
        return "Artwork{" +
                "username='" + username + '\'' +
                ", picture=" + picture +
                ", subtitle='" + subtitle + '\'' +
                ", introduce='" + introduce + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }
}
