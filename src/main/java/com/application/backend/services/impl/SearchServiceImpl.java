package com.application.backend.services.impl;

import com.application.backend.entity.Artworks;
import com.application.backend.entity.Favorite;
import com.application.backend.entity.UserInfo;
import com.application.backend.mapper.ArtWorkMapper;
import com.application.backend.mapper.UserInfoMapper;
import com.application.backend.services.ArtworkService;
import com.application.backend.services.SearchService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService{
    /*@Value(("${web.picture-data-res-path}"))
    private String artWorksRes;
    @Value(("${web.user-photo-res-path}"))
    private String photoRes;*/
    @Value(("${web.user-background-res-path}"))
    private String photoBackRes;
    @Value(("${web.picture-data-res-path-compressed}"))
    private String resCompressed;
    @Value(("${web.user-photo-res-path-compressed}"))
    private String resUserCompressed;
    @Autowired
    private ArtworkService artworkService=new ArtworkServiceImpl();
    @Autowired
    private ArtWorkMapper artWorkMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public IPage searchArtworks(String token,String search, int pageNum) {
        Page<Artworks> page=new Page<>(pageNum,10);
        QueryWrapper<Artworks> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("pid",search).or().like("subtitle",search);
        IPage<Artworks> iPage=artWorkMapper.selectPage(page,queryWrapper);
        List<Artworks> list=iPage.getRecords();
        List<Favorite> favorites=artworkService.getFavorite(token);
        List<UserInfo> userInfos=userInfoMapper.selectList(null);
        userinfoUrlAppender(userInfos);
        artworksUrlAppender(list);
        for (Artworks artworks:list){
            for (Favorite favorite:favorites){
                if (artworks.getPid()==favorite.getPid()){
                    artworks.setLiked(true);
                    break;
                }else {
                    artworks.setLiked(false);
                }
            }
            for (UserInfo userInfo:userInfos){
                if(artworks.getUid()==userInfo.getIid()){
                    artworks.setUserInfo(userInfo);
                    break;
                }
            }
        }
        return iPage;
    }

    @Override
    public IPage searchUser(String search, int pageNUm) {
        Page<UserInfo> page=new Page<>(pageNUm,10);
        QueryWrapper<UserInfo> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("iid",search).or().like("nickname",search);
        IPage<UserInfo> iPage= userInfoMapper.selectPage(page,queryWrapper);
        List<UserInfo> userInfos=iPage.getRecords();
        for (UserInfo userInfo:userInfos){
            List<Artworks> list=userInfoMapper.queryUserArtworksView(userInfo.getIid(),userInfo.getNickname());
            artworksUrlAppender(list);
            userInfo.setArtworks(list);
        }
        userinfoUrlAppender(userInfos);
        return iPage;
    }
    public void userinfoUrlAppender(List<UserInfo> list){
        for (UserInfo userInfo:list){
            userInfo.setUserimage(resUserCompressed+userInfo.getUserimage());
            userInfo.setBackground(photoBackRes+userInfo.getBackground());
        }
    }
    public void artworksUrlAppender(List<Artworks> list){
        for (Artworks artworks:list){
            artworks.setPicture(resCompressed+artworks.getPicture());
        }
    }
}
