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
                break;
            case "D:\\SpringWebData\\UserImg":
                imageResolutionCompress(fileName,ResUrl.USER_TITLE_IMAGE_COMPRESS_PATH,rote,720,720);
                break;
            case "D:\\SpringWebData\\Res":
                imageResolutionCompress(fileName,ResUrl.USER_SUBMIT_ARTWORK_COMPRESS_PATH,rote,1040,600);
                break;
        }
    }
    private static void imageResolutionCompress(String fileNane,String url,String rote,int x,int y) throws IOException {
        Thumbnails.of(new File(rote+"\\"+fileNane))
                .size(x,y)
                .outputQuality(1)
                .toFile(url+"\\"+fileNane);
    }
}
