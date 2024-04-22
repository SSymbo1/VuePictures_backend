package com.application.backend.utils;

import com.application.backend.entity.Result;
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
    public Result uploadUserBackground(MultipartFile file){
        createFolderIfNotExist(ResUrl.USER_BACKGROUND_IMAGE_PATH);
        String filName= getUUIDFileName(file);
        try {
            byte[] bytes=file.getBytes();
            Files.write(Paths.get(ResUrl.USER_BACKGROUND_IMAGE_PATH+"\\"+filName),bytes);
            ImgCompressUtil.imgSizeCompress(filName,ResUrl.USER_BACKGROUND_IMAGE_PATH);
        }catch (IOException e){
            e.printStackTrace();
            return Result.file_upload_background_error();
        }
        return Result.file_upload_success(filName);
    }
    public Result uploadUserTitleImage(MultipartFile file){
        createFolderIfNotExist(ResUrl.USER_TITLE_IMAGE_PATH);
        String filName= getUUIDFileName(file);
        try {
            byte[] bytes=file.getBytes();
            Files.write(Paths.get(ResUrl.USER_TITLE_IMAGE_PATH+"\\"+filName),bytes);
            ImgCompressUtil.imgSizeCompress(filName,ResUrl.USER_TITLE_IMAGE_PATH);
        }catch (IOException e){
            e.printStackTrace();
            return Result.file_upload_user_error();
        }
        return Result.file_upload_success(filName);
    }
    public Result uploadArtworkImage(MultipartFile file){
        createFolderIfNotExist(ResUrl.USER_SUBMIT_ARTWORK_PATH);
        String filName= getUUIDFileName(file);
        try {
            byte[] bytes=file.getBytes();
            Files.write(Paths.get(ResUrl.USER_SUBMIT_ARTWORK_PATH+"\\"+filName),bytes);
            ImgCompressUtil.imgSizeCompress(filName,ResUrl.USER_SUBMIT_ARTWORK_PATH);
        }catch (IOException e){
            e.printStackTrace();
            return Result.file_upload_artworks_error();
        }
        return Result.file_upload_success(filName);
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
