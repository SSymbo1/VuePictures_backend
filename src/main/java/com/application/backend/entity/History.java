package com.application.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class History {
    private int uid;
    private int pid;
    @TableField(exist = false)
    private String token;
    private long viewtime;
    @TableField(exist = false)
    private Artworks artworks;

    public History(int uid, int pid, String token, long viewtime) {
        this.uid = uid;
        this.pid = pid;
        this.token = token;
        this.viewtime = viewtime;
    }

    public History() {
        super();
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public long getViewtime() {
        return viewtime;
    }

    public void setViewtime(long viewtime) {
        this.viewtime = viewtime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Artworks getArtworks() {
        return artworks;
    }

    public void setArtworks(Artworks artworks) {
        this.artworks = artworks;
    }
}
