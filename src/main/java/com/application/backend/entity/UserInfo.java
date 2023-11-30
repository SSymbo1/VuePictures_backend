package com.application.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("userinfo")
public class UserInfo {
    private int iid;
    private String background;
    private String userimage;
    private String nickname;
    private String self;
    private String sex;
    private long birthday;
    private String email;
    @TableField(exist = false)
    private List<Artworks> artworks;
    @TableField(exist = false)
    private String followTime;
}
