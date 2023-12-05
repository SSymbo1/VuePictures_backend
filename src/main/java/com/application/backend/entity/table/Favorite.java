package com.application.backend.entity.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private int uid;
    private int pid;
    private long favoritetime;
}
