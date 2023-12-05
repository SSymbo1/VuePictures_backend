package com.application.backend.entity.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Block {
    private int uid;
    private int ban;
    private long banTime;
}
