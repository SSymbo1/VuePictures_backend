package com.application.backend.entity;

import com.application.backend.entity.table.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private Integer to;
    private Integer from;
    private String message;
    private String time;
    private List<UserInfo> userInfos;
}
