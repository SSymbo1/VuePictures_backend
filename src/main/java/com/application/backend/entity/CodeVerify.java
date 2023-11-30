package com.application.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeVerify {
    private String token;
    private String captcha;
    private String inputCaptcha;
}
