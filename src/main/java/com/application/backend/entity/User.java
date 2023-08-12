package com.application.backend.entity;
import lombok.Data;

@Data
public class User {
    private int uid;
    private String username;
    private String password;
    private int admin;
    private long createtime;

    public User(int uid, String username, String password, int admin, long createtime) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", admin=" + admin +
                ", createtime=" + createtime +
                '}';
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
