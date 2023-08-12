package com.application.backend.mapper;

import com.application.backend.entity.User;
import com.application.backend.entity.UserInfo;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user WHERE username=#{username}")
    List<User> queryUser(String username);
    @Select("SELECT * FROM user WHERE username=#{username} AND password=#{password}")
    List<User> queryUserByUsrAndPwd(String username,String password);
    @Select("SELECT iid,background,userimage,nickname,self,sex,birthday,email FROM userinfo,user WHERE user.uid=userinfo.iid AND username=#{username}")
    List<UserInfo> queryUserInfo(String username);
    @Select("SELECT userimage FROM userinfo,user WHERE user.uid=userinfo.iid AND username=#{username}")
    String queryUserImage(String username);
    @Select("SELECT COUNT(fan) FROM follow WHERE uid=#{uid}")
    int queryFansNumber(int uid);
    @Select("SELECT COUNT(uid) FROM follow WHERE fan=#{fan}")
    int queryFollowNumber(int fan);
    @Select("SELECT * FROM userinfo WHERE iid=#{uid}")
    List<UserInfo> queryUserInfoById(int uid);
    @Select("SELECT * FROM follow WHERE uid=#{uid} AND fan=#{fan}")
    List<Integer> queryFollow(int uid,int fan);
    @Insert("INSERT INTO user (username, password, admin, createtime) VALUE (#{username},#{password},#{admin},#{createtime})")
    int insertIntoUser(String username,String password,int admin,long createtime);
    @Delete("DELETE FROM follow WHERE uid=#{uid} AND fan=#{fan}")
    int deleteFromFollow(int uid,int fan);
    @Insert("INSERT INTO userinfo VALUE (#{iid},#{background},#{userimage},#{nickname},#{self},#{sex},#{birthday},#{email})")
    int insertIntoUserInfo(int iid,String background,String userimage,String nickname,String self,String sex,long birthday,String email);
    @Insert("INSERT INTO follow VALUE(#{uid},#{fan})")
    int insertIntoFollow(int uid,int fan);
    @Update("UPDATE userinfo SET background=#{background} WHERE iid=#{iid}")
    int updateUserBackgroundImage(String background,int iid);
    @Update("UPDATE userinfo SET userimage=#{userimage} WHERE iid=#{iid}")
    int updateUserTitleHeadImage(String userimage,int iid);
    @Update("UPDATE userinfo SET nickname=#{nickname},self=#{self},sex=#{sex},birthday=#{birthday},email=#{email} WHERE iid=#{iid}")
    int updateUserSelfInfo(int iid,String nickname,String self,String sex,long birthday,String email);
}