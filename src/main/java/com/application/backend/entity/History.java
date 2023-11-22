package com.application.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class History {
    private int uid;
    private int pid;
    @TableField(exist = false)
    private String token;
    private long viewtime;
    @TableField(exist = false)
    private Artworks artworks;

    public History(int uid, int pid, String token, long viewtime) {
        this.uid = uid;
        this.pid = pid;
        this.token = token;
        this.viewtime = viewtime;
    }
}
