package function;

import com.application.backend.utils.ImgCompressUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Compress {
    private static final String ROOT_PATH="D:";
    private static final String USER_BACKGROUND_IMAGE_PATH=ROOT_PATH+"\\SpringWebData\\UserBk";
    private static final String USER_TITLE_IMAGE_PATH=ROOT_PATH+"\\SpringWebData\\UserImg";
    private static final String USER_SUBMIT_ARTWORK_PATH=ROOT_PATH+"\\SpringWebData\\Res";
    public static void main(String[] args) {
        Copy usr=new Copy(USER_TITLE_IMAGE_PATH);
        Copy back=new Copy(USER_BACKGROUND_IMAGE_PATH);
        Copy img1=new Copy(USER_SUBMIT_ARTWORK_PATH);
        Thread user=new Thread(usr);
        Thread background=new Thread(back);
        Thread artworks=new Thread(img1);
        user.start();
        background.start();
        artworks.start();
    }
    public static class Copy implements Runnable{
        private final String url;
        private final Object lock=new Object();
        public Copy(String url){
            this.url=url;
            System.out.println(this+"压缩线程已执行");
        }
        @Override
        public void run() {
            synchronized (lock){
                List<String> fileName=new ArrayList<>();
                File[] files=new File(url).listFiles();
                if (files != null) {
                    for (File f:files){
                        fileName.add(f.getName());
                    }
                }
                for (String str:fileName){
                    try {
                        ImgCompressUtil.imgSizeCompress(str,url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(url+"下的"+str+"已压缩");
                }
            }
        }
    }
}
