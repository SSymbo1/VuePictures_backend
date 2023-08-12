package com.application.backend.services.impl;

import com.application.backend.entity.Artwork;
import com.application.backend.entity.Artworks;
import com.application.backend.entity.Favorite;
import com.application.backend.mapper.ArtWorkMapper;
import com.application.backend.mapper.UserMapper;
import com.application.backend.services.ArtworkServices;
import com.application.backend.utils.FileUploadUtil;
import com.application.backend.utils.JwtUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

@Slf4j
@Service
public class ArtworkServiceImpl implements ArtworkServices {
    @Autowired
    private ArtWorkMapper artWorkMapper;
    @Autowired
    private UserMapper userMapper;
    @Value(("${web.picture-data-res-path}"))
    private String artWorksRes;
    private String getJarFilePath() {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        return jarFile.getParentFile().toString();
    }
    @Override
    public List<Artworks> getAllArtworks() {
        List<Artworks> list=artWorkMapper.queryAllArtworks();
        for (Artworks artworks:list){
            artworks.setPicture(artWorksRes+artworks.getPicture());
        }
        return list;
    }

    @Override
    public IPage getAllArtworksPaged(String token,int pageNum) {
        Page<Artworks> page=new Page<>(pageNum,10);
        List<Favorite> favoriteList = getFavorite(token);
        IPage<Artworks> iPage=artWorkMapper.selectPage(page,null);
        List<Artworks> list=iPage.getRecords();
        for (Artworks artworks:list){
            artworks.setPicture(artWorksRes+artworks.getPicture());
            for (Favorite favorite:favoriteList){
                if (artworks.getPid()==favorite.getPid()){
                    artworks.setLiked(true);
                    break;
                }else {
                    artworks.setLiked(false);
                }
            }
        }
        return iPage;
    }

    @Override
    public List<Artworks> getRandArtworks(int num) {
        List<Artworks> list=artWorkMapper.queryRandArtworks(num);

        for (Artworks artworks:list){
            artworks.setPicture(artWorksRes+artworks.getPicture());
        }
        return list;
    }

    @Override
    public List<Artworks> getHotArtworks(int num) {
        List<Artworks> list=artWorkMapper.queryMostLikedArtworks(num);
        for (Artworks artworks:list){
            artworks.setPicture(artWorksRes+artworks.getPicture());
        }
        return list;
    }

    @Override
    public List<Artworks> getArtworksById(int id) {
        List<Artworks> list=artWorkMapper.queryArtworksByPid(id);
        for (Artworks artworks:list){
            artworks.setPicture(artWorksRes+artworks.getPicture());
        }
        return list;
    }

    @Override
    public boolean artworksViewed(int pid) {
        int i = artWorkMapper.updateArtworksView(pid);
        return i != 0;
    }

    @Override
    public byte[] downloadArtworks(int pid) throws IOException {
        List<Artworks> list=artWorkMapper.queryArtworksByPid(pid);
        Artworks artworks=list.get(0);
        String format=artworks.getPicture().substring(artworks.getPicture().length()-3);
        artworks.setPicture(getJarFilePath()+"\\SpringWebData"+"\\Res\\"+artworks.getPicture());
        BufferedImage image=ImageIO.read(new File(artworks.getPicture()));
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        ImageIO.write(image,format,os);
        return os.toByteArray();
    }

    @Override
    public boolean updateArtworksViewTime(int pid) {
        return artWorkMapper.updateArtworksLastViewTime(pid,System.currentTimeMillis())!=0;
    }

    @Override
    public boolean artworksFavorite(String token, int pid) {
        int uid = userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        List<Favorite> list=artWorkMapper.queryFavoriteByUidPid(uid,pid);
        if (list.isEmpty()){
            int i = artWorkMapper.updateArtworksLikeNumPlus(pid);
            return artWorkMapper.insertIntoFavorite(uid,pid,System.currentTimeMillis())!=0;

        }else {
            int i = artWorkMapper.updateArtworksLikeNumReduce(pid);
            return artWorkMapper.deleteFavorite(list.get(0).getUid(),pid)!=0;
        }
    }

    @Override
    public boolean artworksFavoriteStatue(String token, int pid) {
        int uid = userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        List<Favorite> list=artWorkMapper.queryFavoriteByUidPid(uid, pid);
        return list.isEmpty();
    }

    @Override
    public List<Favorite> getFavorite(String token) {
        int uid = userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        return artWorkMapper.queryFavoriteByUid(uid);
    }

    @Override
    public List<Artworks> getUserArtworks(int uid) {
        List<Artworks> list = artWorkMapper.queryArtworksByUid(uid);
        List<Favorite> list1 = artWorkMapper.queryFavoriteByUid(uid);
        for (Artworks artworks:list){
            artworks.setPicture(artWorksRes+artworks.getPicture());
            for (Favorite favorite:list1){
                if (artworks.getPid()==favorite.getPid()){
                    artworks.setLiked(true);
                    break;
                }else {
                    artworks.setLiked(false);
                }
            }
        }
        return list;
    }

    @Override
    public List<Artworks> getFavoriteArtworks(int uid) {
        List<Artworks> list = artWorkMapper.queryFavoriteArtworks(uid);
        List<Favorite> list1=artWorkMapper.queryFavoriteByUid(uid);
        for (Artworks artworks:list){
            artworks.setPicture(artWorksRes+artworks.getPicture());
            for (Favorite favorite:list1){
                if (artworks.getPid()==favorite.getPid()){
                    artworks.setLiked(true);
                    break;
                }else {
                    artworks.setLiked(false);
                }
            }
        }
        return list;
    }

    @Override
    public boolean submitArtwork(MultipartFile file, String username, String subtitle, String introduce) {
        FileUploadUtil fileUploadUtil=new FileUploadUtil();
        int uid = userMapper.queryUser(JwtUtil.parseJWT(username).getSubject()).get(0).getUid();
        String picture = fileUploadUtil.uploadArtworkImage(file);
        return artWorkMapper.insertIntoArtworks(uid, picture, subtitle, System.currentTimeMillis(), introduce)!=0;
    }
}
