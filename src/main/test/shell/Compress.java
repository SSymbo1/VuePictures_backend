package shell;

import com.application.backend.utils.ImgCompressUtil;
import com.application.backend.utils.ResUrl;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Compress {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Copy usr=new Copy(ResUrl.USER_TITLE_IMAGE_PATH);
        Copy back=new Copy(ResUrl.USER_BACKGROUND_IMAGE_PATH);
        Copy img=new Copy(ResUrl.USER_SUBMIT_ARTWORK_PATH);
        executorService.execute(usr);
        executorService.execute(back);
        executorService.execute(img);
        executorService.shutdown();
    }
    public static class Copy implements Runnable{
        private final String url;
        public Copy(String url){
            this.url=url;
            System.out.println(this+"压缩线程已执行");
        }
        @Override
        public void run() {
            File[] files=new File(url).listFiles();
            if (files != null) {
                for (File f:files){
                    try {
                        ImgCompressUtil.imgSizeCompress(f.getName(),url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(this+"线程下的"+url+"下的"+f.getName()+"已压缩");
                }
            }
        }
    }
}
