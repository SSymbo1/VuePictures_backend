package com.application.backend.mapper;

import com.application.backend.entity.Artworks;
import com.application.backend.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    @Select("SELECT pid, uid, picture, subtitle, likenum, createtime, introduce, view, lastviewtime FROM artworks,userinfo WHERE artworks.uid=userinfo.iid and artworks.uid=#{uid} AND del=0 OR userinfo.nickname=#{uid} LIMIT 3")
    List<Artworks> queryUserArtworksView(int uid,String nickname);
}
