package com.application.backend.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//后端文件虚拟接口映射配置类
public class InterceptorConfig implements WebMvcConfigurer {
    @SneakyThrows
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/resUser/**")
                .addResourceLocations("file:///"+"D:"+"/SpringWebData"+"/UserImg/");
        System.out.println("file:///"+"D:"+"/SpringWebData"+"/UserImg/");

        registry.addResourceHandler("/resBk/**")
                .addResourceLocations("file:///"+"D:"+"/SpringWebData"+"/UserBK/");
        System.out.println("file:///"+"D:"+"/SpringWebData"+"/UserBK/");

        registry.addResourceHandler("/artWorks/**")
                .addResourceLocations("file:///"+"D:"+"/SpringWebData"+"/Res/");
        System.out.println("file:///"+"D:"+"/SpringWebData"+"/Res/");

        registry.addResourceHandler("/resCompressed/**")
                .addResourceLocations("file:///"+"D:"+"/SpringWebData"+"/Compress"+"/Res/");
        System.out.println("file:///"+"D:"+"/SpringWebData"+"/Compress"+"/Res/");

        registry.addResourceHandler("/resUserCompressed/**")
                .addResourceLocations("file:///"+"D:"+"/SpringWebData"+"/Compress"+"/UserImg/");
        System.out.println("file:///"+"D:"+"/SpringWebData"+"/Compress"+"/UserImg/");
    }

    /*private String getJarFilePath() {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        return jarFile.getParentFile().toString();
    }*/
}
