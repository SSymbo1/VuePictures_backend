package com.application.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Artworks {
    private int pid;
    private int uid;
    private String picture;
    private String subtitle;
    private int likenum;
    private long createtime;
    private String introduce;
    private long view;
    private long lastviewtime;
    @TableField(exist = false)
    private boolean isLiked;
    @TableField(exist = false)
    private UserInfo userInfo;

    public Artworks(int pid, int uid, String picture, String subtitle, int likenum, long createtime, String introduce, long view, long lastviewtime) {
        this.pid = pid;
        this.uid = uid;
        this.picture = picture;
        this.subtitle = subtitle;
        this.likenum = likenum;
        this.createtime = createtime;
        this.introduce = introduce;
        this.view = view;
        this.lastviewtime = lastviewtime;
    }

    @Override
    public String toString() {
        return "Artworks{" +
                "pid=" + pid +
                ", uid=" + uid +
                ", picture='" + picture + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", likenum=" + likenum +
                ", createtime=" + createtime +
                '}';
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    public long getLastviewtime() {
        return lastviewtime;
    }

    public void setLastviewtime(long lastviewtime) {
        this.lastviewtime = lastviewtime;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getLikenum() {
        return likenum;
    }

    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
