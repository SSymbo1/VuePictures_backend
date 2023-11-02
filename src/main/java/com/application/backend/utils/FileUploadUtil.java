package com.application.backend.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Slf4j
@Component
public class FileUploadUtil {
    /*private String getJarFilePath() {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        return jarFile.getParentFile().toString();
    }*/
    public String uploadUserBackground(MultipartFile file){
        createFolderIfNotExist(ResUrl.USER_BACKGROUND_IMAGE_PATH);
        String filName= getUUIDFileName(file);
        try {
            byte[] bytes=file.getBytes();
            Files.write(Paths.get(ResUrl.USER_BACKGROUND_IMAGE_PATH+"\\"+filName),bytes);
            ImgCompressUtil.imgSizeCompress(filName,ResUrl.USER_BACKGROUND_IMAGE_PATH);
        }catch (IOException e){
            e.printStackTrace();
            return "wrong";
        }
        return filName;
    }
    public String uploadUserTitleImage(MultipartFile file){
        createFolderIfNotExist(ResUrl.USER_TITLE_IMAGE_PATH);
        String filName= getUUIDFileName(file);
        try {
            byte[] bytes=file.getBytes();
            Files.write(Paths.get(ResUrl.USER_TITLE_IMAGE_PATH+"\\"+filName),bytes);
            ImgCompressUtil.imgSizeCompress(filName,ResUrl.USER_TITLE_IMAGE_PATH);
        }catch (IOException e){
            e.printStackTrace();
            return "wrong";
        }
        return filName;
    }
    public String uploadArtworkImage(MultipartFile file){
        createFolderIfNotExist(ResUrl.USER_SUBMIT_ARTWORK_PATH);
        String filName= getUUIDFileName(file);
        try {
            byte[] bytes=file.getBytes();
            Files.write(Paths.get(ResUrl.USER_SUBMIT_ARTWORK_PATH+"\\"+filName),bytes);
            ImgCompressUtil.imgSizeCompress(filName,ResUrl.USER_SUBMIT_ARTWORK_PATH);
        }catch (IOException e){
            e.printStackTrace();
            return "wrong";
        }
        return filName;
    }
    private void createFolderIfNotExist(String folder){
        File root=new File(ResUrl.ROOT_PATH);
        if (!root.exists()){
            root.mkdirs();
            File background=new File(folder);
            if (!background.exists()){
                background.mkdirs();
            }
        }
    }
    private String getUUIDFileName(MultipartFile file){
        return UUID.randomUUID()+"_"+file.getOriginalFilename();
    }
}
