package com.application.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
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

    public UserInfo(int iid, String background, String userimage, String nickname, String self, String sex, long birthday, String email) {
        this.iid = iid;
        this.background = background;
        this.userimage = userimage;
        this.nickname = nickname;
        this.self=self;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
    }
}
