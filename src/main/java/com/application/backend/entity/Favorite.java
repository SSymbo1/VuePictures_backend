package com.application.backend.entity;

import lombok.Data;

@Data
public class Favorite {
    private int uid;
    private int pid;
    private long favoritetime;

    public Favorite(int uid, int pid, long favoritetime) {
        this.uid = uid;
        this.pid = pid;
        this.favoritetime = favoritetime;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "uid=" + uid +
                ", pid=" + pid +
                ", favoritetime=" + favoritetime +
                '}';
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

    public long getFavoritetime() {
        return favoritetime;
    }

    public void setFavoritetime(long favoritetime) {
        this.favoritetime = favoritetime;
    }
}
