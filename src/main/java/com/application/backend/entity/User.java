package com.application.backend.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private int uid;
    private String username;
    private String password;
    @TableField(exist = false)
    private String email;
    @TableField(exist = false)
    private String captcha;
    @TableField(exist = false)
    private String code;
    private int admin;
    private long createtime;
    private int ban;
    private int del;

    public User(int uid, String username, String password, int admin, long createtime,int ban, int del) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.createtime = createtime;
        this.ban=ban;
        this.del=del;
    }
}
