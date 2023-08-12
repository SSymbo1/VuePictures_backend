package com.application.backend.controller;

import com.application.backend.entity.Artwork;
import com.application.backend.entity.Artworks;
import com.application.backend.services.ArtworkServices;
import com.application.backend.services.impl.ArtworkServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class ArtworksController {
    @Autowired
    private final ArtworkServices artworkServices=new ArtworkServiceImpl();
    @GetMapping("/artworks") //获取所有插画接口（查询结果未分页）
    public List<Artworks> getAllArtworks(){
        return artworkServices.getAllArtworks();
    }
    @GetMapping("/getHeaderImg") //获取首页header插画接口，返回4张随机插画
    public List<Artworks> getRandTitleArtworks(){
        return artworkServices.getRandArtworks(4);
    }
    @GetMapping("/getHotImg") //获取热门插画接口，返回3张热门插画
    public List<Artworks> getHotArtworks(){
        return artworkServices.getHotArtworks(3);
    }
    @GetMapping("/getArtworksP") //返回所有插画接口（查询结果分页，每页10张）
    public IPage getArtworksPaged(String token,int page){
        return artworkServices.getAllArtworksPaged(token,page);
    }
    @GetMapping("/getArtworksId") //获取插画接口，提供插画id(pid)，返回Artworks封装的图片详细信息集合
    public List<Artworks> getArtworksById(int pid){
        return artworkServices.getArtworksById(pid);
    }
    @GetMapping("/view") //插画浏览量增加接口，提供插画id(pid),返回是否该图片浏览量增加boolean
    public boolean userViewArtworks(int pid){
        return artworkServices.artworksViewed(pid);
    }
    @GetMapping("/updateTime") //插画最近浏览时间更新接口，提供插画id(pid)，返回更新操作是否成功boolean
    public boolean updateArtworks(int pid){
        return artworkServices.updateArtworksViewTime(pid);
    }
    @GetMapping(value = "/download",produces = MediaType.IMAGE_JPEG_VALUE) //插画下载接口，提供提供插画id(pid)，返回.jpg图片格式blob流
    public byte[] downloadArtworks(@RequestParam("pid")int pid, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        return artworkServices.downloadArtworks(pid);
    }
    @GetMapping("/favoriteStatue") //查询收藏状态接口，提供token，图片id（pid），返回该用户是否收藏该图片
    public boolean favoriteStatue(String token,int pid){
        return artworkServices.artworksFavoriteStatue(token, pid);
    }
    @GetMapping("/favorite") //收藏（取消收藏）接口，提供token，图片id（pid），返回操作是否成功
    public boolean favorite(String token,int pid){
        return artworkServices.artworksFavorite(token,pid);
    }
    @GetMapping("/getWorks")
    public List<Artworks> getArtworksByUid(int uid){
        return artworkServices.getUserArtworks(uid);
    }
    @GetMapping("/getFavorite")
    public List<Artworks> getFavorite(int uid){
        return artworkServices.getFavoriteArtworks(uid);
    }
    @PostMapping("/submit")
    public boolean submit(@RequestBody MultipartFile file,@RequestParam("username") String username,@RequestParam("subtitle")String subtitle,@RequestParam("introduce") String introduce){
        return artworkServices.submitArtwork(file,username,subtitle,introduce);
    }
}
