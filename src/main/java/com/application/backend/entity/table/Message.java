package com.application.backend.entity.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int mid;
    private int send;
    private int accept;
    private String message;
    private long time;
}
