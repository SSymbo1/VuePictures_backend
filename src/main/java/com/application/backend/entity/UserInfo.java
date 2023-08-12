package com.application.backend.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "iid=" + iid +
                ", background='" + background + '\'' +
                ", userimage='" + userimage + '\'' +
                ", nickname='" + nickname + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                '}';
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
