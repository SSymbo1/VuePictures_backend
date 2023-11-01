package com.application.backend.utils;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.CodeGenerator;

import java.awt.*;
import java.util.Random;

// 验证码工具类，基于HuoTools,但文档还没看明白
public class CaptchaUtil extends AbstractCaptcha {
    public CaptchaUtil(int width, int height, int codeCount, int interfereCount) {
        super(width, height, codeCount, interfereCount);
    }
    public CaptchaUtil(int width, int height, CodeGenerator generator, int interfereCount) {
        super(width, height, generator, interfereCount);
    }
    @Override
    protected Image createImage(String s) {
        LineCaptcha lineCaptcha = new LineCaptcha(130, 40);
        return null;
    }
    public static String randomCode(){
        Random random=new Random();
        StringBuilder captcha= new StringBuilder();
        for (int i=0;i<6;i++){
            captcha.append(random.nextInt(10));
        }
        return captcha.toString();
    }
}
