package com.application.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artwork {
    private int pid;
    private String subtitle;
    private String introduce;
}
