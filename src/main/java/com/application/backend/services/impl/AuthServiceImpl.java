package com.application.backend.services.impl;

import com.application.backend.entity.Result;
import com.application.backend.mapper.UserMapper;
import com.application.backend.services.AuthService;
import com.application.backend.utils.CaptchaUtil;
import com.application.backend.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserMapper userMapper;
    @Value("${spring.mail.username}")
    private String captchaServer;
    @Override
    public Result emailCaptcha(String emailUrl) {
        String captcha= CaptchaUtil.randomCode();
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message);
            helper.setFrom(captchaServer);
            helper.setTo(emailUrl);
            helper.setSubject("【VuePictures —— 一个正在测试的插画网站】邮箱验证");
            helper.setText("<h1>欢迎成为VuePictures大家庭中的一员</h1>"+
                    "<h5>您正在进行邮箱验证码验证操作，本次验证码为：<span style='color: red;font-size: 20px'>"+captcha+"</span></h5>"+
                    "<h5>请不要暴露您的验证码给任何人，如非您本人操作，请忽略本邮件。不需要回复本邮件</h5>"+
                    "<h5 style='text-align: right'>————SSymbol</h5>",true);
        } catch (MessagingException e) {
            log.warn("邮件发送失败"+e.getMessage());
        }
        try {
            javaMailSender.send(message);
            return Result.email_success().data("captcha",captcha);
        }catch (Exception e){
            log.warn("邮件发送失败"+e.getMessage());
            return Result.email_error();
        }
    }

    @Override
    public Result deleteSubmitCaptcha(String emailUrl,String token) {
        String captcha= CaptchaUtil.randomCode();
        String username=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUsername();
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message);
            helper.setFrom(captchaServer);
            helper.setTo(emailUrl);
            helper.setSubject("【VuePictures —— 一个正在测试的插画网站】危险操作：删除所有投稿操作验证");
            helper.setText("<h1>注意！您的账号："+username+"正在执行危险操作：<span style='color:red'>【删除所有投稿】</span></h1>"+
                    "<h5>请确认是否是您本人操作，本次验证码为：<span style='color: red;font-size: 20px'>"+captcha+"</span></h5>"+
                    "<h5>请不要暴露您的验证码给任何人，如非您本人操作，请忽略本邮件。不需要回复本邮件</h5>"+
                    "<h5 style='text-align: right'>————SSymbol</h5>",true);
        } catch (MessagingException e) {
            log.warn("邮件发送失败"+e.getMessage());
        }
        try {
            javaMailSender.send(message);
            return Result.email_success().data("captcha",captcha);
        }catch (Exception e){
            log.warn("邮件发送失败"+e.getMessage());
            return Result.email_error();
        }
    }
}
