package com.application.backend.config;

import lombok.SneakyThrows;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.io.File;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @SneakyThrows
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/resUser/**")
                .addResourceLocations("file:///"+getJarFilePath()+"/SpringWebData"+"/UserImg/");
        System.out.println("file:///"+getJarFilePath()+"/SpringWebData"+"/UserImg/");
        registry.addResourceHandler("/resBk/**")
                .addResourceLocations("file:///"+getJarFilePath()+"/SpringWebData"+"/UserBK/");
        System.out.println("file:///"+getJarFilePath()+"/SpringWebData"+"/UserBK/");
        registry.addResourceHandler("/artWorks/**")
                .addResourceLocations("file:///"+getJarFilePath()+"/SpringWebData"+"/Res/");
        System.out.println("file:///"+getJarFilePath()+"/SpringWebData"+"/Res/");
    }
    private String getJarFilePath() {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        return jarFile.getParentFile().toString();
    }
}
