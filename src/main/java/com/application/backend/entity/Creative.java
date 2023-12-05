package com.application.backend.entity;

import com.application.backend.entity.table.Artworks;
import com.application.backend.entity.table.Favorite;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Creative {
    @TableField(exist = false)
    private Artworks lastSubmit;
    @TableField(exist = false)
    private Artworks lastView;
    @TableField(exist = false)
    private Favorite lastFavorite;
    @TableField(exist = false)
    private int fans;
    @TableField(exist = false)
    private int views;
}
