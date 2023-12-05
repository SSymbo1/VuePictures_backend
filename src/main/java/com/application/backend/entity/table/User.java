package com.application.backend.entity.table;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int uid;
    private String username;
    private String password;
    private int admin;
    private long createtime;
    private int ban;
    private int del;
    @TableField(exist = false)
    private String email;
    @TableField(exist = false)
    private String captcha;
    @TableField(exist = false)
    private String code;
}
