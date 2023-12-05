package com.application.backend.entity.table;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private int ban;
    private int del;
    @TableField(exist = false)
    private boolean isLiked;
    @TableField(exist = false)
    private UserInfo userInfo;
    @TableField(exist = false)
    private String createtimeString;
}
