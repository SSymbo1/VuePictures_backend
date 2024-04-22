package com.application.backend.services.impl;

import cn.hutool.core.date.DateUtil;
import com.application.backend.entity.table.Artworks;
import com.application.backend.entity.table.Favorite;
import com.application.backend.entity.table.Follow;
import com.application.backend.entity.table.UserInfo;
import com.application.backend.mapper.ArtWorkMapper;
import com.application.backend.mapper.UserInfoMapper;
import com.application.backend.mapper.UserMapper;
import com.application.backend.services.ArtworkService;
import com.application.backend.services.SearchService;
import com.application.backend.utils.JwtUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SearchServiceImpl implements SearchService{
    @Value(("${web.user-background-res-path}"))
    private String photoBackRes;
    @Value(("${web.picture-data-res-path-compressed}"))
    private String resCompressed;
    @Value(("${web.user-photo-res-path-compressed}"))
    private String resUserCompressed;
    @Value(("${web.user-photo-res-path}"))
    private String photoRes;
    @Autowired
    private ArtworkService artworkService=new ArtworkServiceImpl();
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArtWorkMapper artWorkMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public IPage<Artworks> searchArtworks(String token,String search, int pageNum) {
        Page<Artworks> page=new Page<>(pageNum,9);
        QueryWrapper<Artworks> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("del",0).and(condition->condition.like("pid",search).or().like("subtitle",search));
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
    public IPage<UserInfo> searchUser(String search, int pageNUm) {
        Page<UserInfo> page=new Page<>(pageNUm,9);
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

    @Override
    public IPage<Artworks> artworksManagerSearcher(String token, String keyword, int pageNum) {
        int uid=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        Page<Artworks> page=new Page<>(pageNum,9);
        QueryWrapper<Artworks> queryWrapper=new QueryWrapper<>();
        if (keyword==null||"".equals(keyword)){
            queryWrapper.eq("uid",uid).and(condition->condition.eq("del",0));
        }else {
            queryWrapper.eq("uid",uid).and(condition->condition.like("subtitle",keyword)).and(condition->condition.eq("del",0));
        }
        IPage<Artworks> iPage=artWorkMapper.selectPage(page,queryWrapper);
        List<Artworks> artworks=iPage.getRecords();
        for (Artworks artwork:artworks){
            artwork.setPicture(resCompressed+artwork.getPicture());
            Date date=DateUtil.date(artwork.getCreatetime());
            artwork.setCreatetimeString(date.toString());
        }
        return iPage;
    }

    @Override
    public IPage<UserInfo> fansManagerSearcher(String token, String keyword, int pageNum) {
        int pageSize=9;
        int uid=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        IPage<UserInfo> iPage = new Page<>();
        iPage.setCurrent(pageNum);
        iPage.setSize(pageSize);
        List<UserInfo> userInfos=userInfoMapper.queryFansUserInfo(uid);
        if (!userInfos.isEmpty()){
            List<Follow> follows=userMapper.queryUserFollow(uid);
            if (!"".equals(keyword) && keyword != null) {
                userInfos = userInfoMapper.queryFansUserInfoByKeyword(uid, keyword);
            }
            iPage.setTotal(userInfos.size());
            iPage.setPages((int)Math.ceil((double)iPage.getTotal()/iPage.getSize()));
            iPage.setRecords(userInfos.subList((pageNum-1)*pageSize,Math.min(pageNum*pageSize,userInfos.size())));
            for (UserInfo userInfo:userInfos){
                userInfo.setUserimage(photoRes+userInfo.getUserimage());
                userInfo.setBackground(photoBackRes+userInfo.getBackground());
                for (Follow follow:follows){
                    if (follow.getFan()==userInfo.getIid()){
                        Date date=DateUtil.date(follow.getFollowTime());
                        userInfo.setFollowTime(date.toString());
                    }
                }
            }
        }else {
            iPage.setTotal(0);
            iPage.setPages(0);
            iPage.setRecords(new ArrayList<>());
        }
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
