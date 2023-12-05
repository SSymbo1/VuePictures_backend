package com.application.backend.mapper;

import com.application.backend.entity.table.Artworks;
import com.application.backend.entity.table.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    @Select("SELECT pid, uid, picture, subtitle, likenum, createtime, introduce, view, lastviewtime FROM artworks,userinfo WHERE artworks.uid=userinfo.iid AND artworks.uid=#{uid} AND del=0 OR userinfo.nickname=#{uid} LIMIT 3")
    List<Artworks> queryUserArtworksView(int uid,String nickname);
    @Select("SELECT * FROM userinfo WHERE iid IN (SELECT fan FROM follow WHERE uid=#{uid})")
    List<UserInfo> queryFansUserInfo(int uid);
    @Select("SELECT * FROM userinfo WHERE iid IN (SELECT fan FROM follow WHERE uid=14) AND iid LIKE CONCAT('%',#{keyword},'%') OR nickname LIKE CONCAT('%',#{keyword},'%')")
    List<UserInfo> queryFansUserInfoByKeyword(int uid,String keyword);
    @Select("SELECT * FROM userinfo WHERE iid=#{uid}")
    List<UserInfo> queryUserInfoByUid(int uid);
    @Select("SELECT * FROM userinfo WHERE iid IN (SELECT fan FROM follow WHERE uid=#{uid})")
    List<UserInfo> queryUserInfoFollowed(int uid);
}
