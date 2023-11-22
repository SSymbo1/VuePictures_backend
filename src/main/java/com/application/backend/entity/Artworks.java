package com.application.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Artworks {
    private int pid;
    private int uid;
    private String picture;
    private String subtitle;
    private int likenum;
    private long createtime;
    @TableField(exist = false)
    private String createtimeString;
    private String introduce;
    private long view;
    private long lastviewtime;
    @TableField(exist = false)
    private boolean isLiked;
    @TableField(exist = false)
    private UserInfo userInfo;
    private int ban;
    private int del;

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
}
