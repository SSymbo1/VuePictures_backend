package com.application.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artwork {
    String username;
    MultipartFile picture;
    String subtitle;
    String introduce;
}
