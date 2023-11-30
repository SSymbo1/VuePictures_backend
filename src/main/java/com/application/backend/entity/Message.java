package com.application.backend.entity;

import lombok.Data;

import java.util.List;

@Data
public class Message {
    private Integer to;
    private Integer from;
    private String message;
    private String time;
    private List<UserInfo> userInfos;
}
