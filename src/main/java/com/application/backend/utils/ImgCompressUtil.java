package com.application.backend.utils;

import cn.hutool.core.img.Img;
import cn.hutool.core.io.FileUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
@Component
public class ImgCompressUtil {
    public static void imgSizeCompress(String fileName,String rote) throws IOException {
        switch (rote) {
            case "D:\\SpringWebData\\UserBk":
                imageResolutionCompress(fileName,ResUrl.USER_BACKGROUND_IMAGE_COMPRESS_PATH,rote,1441,250);
                //convert(fileName,COMPRESSED_BACK_PATH,rote);
                break;
            case "D:\\SpringWebData\\UserImg":
                imageResolutionCompress(fileName,ResUrl.USER_TITLE_IMAGE_COMPRESS_PATH,rote,720,720);
                //convert(fileName,COMPRESSED_USER_PATH,rote);
                break;
            case "D:\\SpringWebData\\Res":
                imageResolutionCompress(fileName,ResUrl.USER_SUBMIT_ARTWORK_COMPRESS_PATH,rote,1040,600);
                //convert(fileName,COMPRESSED_ARTWORKS_PATH,rote);
                break;
        }
    }
    private static void imageResolutionCompress(String fileNane,String url,String rote,int x,int y) throws IOException {
        Thumbnails.of(new File(rote+"\\"+fileNane))
                .size(x,y)
                .outputQuality(1)
                .toFile(url+"\\"+fileNane);
    }
    /*private static void convert(String fileName,String rote,String url){
        String convertFile;
        if (!fileName.substring(fileName.lastIndexOf('.')+1).equals("jpg")
                ||!fileName.substring(fileName.lastIndexOf('.')+1).equals("JPG")
                ){
            convertFile=fileName.substring(0,fileName.lastIndexOf('.'))+".jpg";
            cn.hutool.core.img.ImgUtil.convert(FileUtil.file(url+"\\"+fileName)
                    ,FileUtil.file(COMPRESSED_TEMP_PATH+"\\"+convertFile));
            Img.from(FileUtil.file(COMPRESSED_TEMP_PATH+"\\"+convertFile))
                    .setQuality(0.1)
                    .write(FileUtil.file(rote+"\\"+convertFile));
        }else {
            Img.from(FileUtil.file(rote + "\\" + fileName))
                    .setQuality(0.1)
                    .write(FileUtil.file(COMPRESSED_BACK_PATH + "\\" + fileName));
        }
        flushTemp();
    }*/
    /*private static void flushTemp(){
        File[] files=new  File(COMPRESSED_TEMP_PATH).listFiles();
        if (files != null) {
            for (File file : files){
                if (file.isFile()){
                    file.delete();
                }
            }
        }
    }*/
}
