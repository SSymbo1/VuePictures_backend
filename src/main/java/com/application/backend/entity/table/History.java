package com.application.backend.entity.table;

import com.application.backend.entity.table.Artworks;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private int uid;
    private int pid;
    private long viewtime;
    @TableField(exist = false)
    private Artworks artworks;
    @TableField(exist = false)
    private String token;
}
