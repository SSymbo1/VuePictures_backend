package com.application.backend.services.impl;

import com.application.backend.entity.Result;
import com.application.backend.entity.User;
import com.application.backend.entity.UserInfo;
import com.application.backend.mapper.UserMapper;
import com.application.backend.services.UserService;
import com.application.backend.utils.FileUploadUtil;
import com.application.backend.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Value(("${web.user-photo-res-path}"))
    private String photoRes;
    @Value(("${web.user-background-res-path}"))
    private String photoBackRes;
    @Override
    public List<User> getUser(String username) {
        return null;
    }

    @Override
    public List<Integer> getUserFansInfo(String token) {
        int uid=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        List<Integer> list=new ArrayList<>();
        list.add(userMapper.queryFansNumber(uid));
        list.add(userMapper.queryFollowNumber(uid));
        return list;
    }

    @Override
    public List<UserInfo> getUserInfoById(int uid) {
        List<UserInfo> list = userMapper.queryUserInfoById(uid);
        for (UserInfo userInfo:list){
            userInfo.setBackground(photoBackRes+userInfo.getBackground());
            userInfo.setUserimage(photoRes+userInfo.getUserimage());
        }
        return list;
    }

    @Override
    public Result userRegister(User user) {
        String token=JwtUtil.createJWT(user.getUsername());
        List<User> list=new ArrayList<>();
        long timestamp=System.currentTimeMillis();
        int code=0;
        try {
            code = userMapper.insertIntoUser(user.getUsername(), user.getPassword(), 0, timestamp);
            list=userMapper.queryUser(user.getUsername());
        }catch (Exception e){
            return Result.register_exist().data("token",token);
        }
        if (code!=0&&!list.isEmpty()){
            int key=userMapper.insertIntoUserInfo(list.get(0).getUid(),"default.png","default.png","User","这个人很懒，没有个人简介哦","none",timestamp,"none");
            return Result.register_ok().data("token",token);
        }else {
            return Result.register_error().data("token",token);
        }
    }

    @Override
    public Result userLogin(User user) {
        String token=JwtUtil.createJWT(user.getUsername());
        List<User> list=userMapper.queryUserByUsrAndPwd(user.getUsername(),user.getPassword());
        if (!list.isEmpty()){
            return Result.ok().data("token",token);
        }else {
            return Result.error().data("token",token);
        }
    }

    @Override
    public String getUserImg(String token) {
        return userMapper.queryUserImage(JwtUtil.parseJWT(token).getSubject());
    }

    @Override
    public List<UserInfo> getUserInfo(String token) {
        List<UserInfo> list=userMapper.queryUserInfo(JwtUtil.parseJWT(token).getSubject());
        list.get(0).setUserimage(photoRes+list.get(0).getUserimage());
        list.get(0).setBackground(photoBackRes+list.get(0).getBackground());
        return list;
    }

    @Override
    public Result userBackgroundChange(MultipartFile file, HttpServletRequest request) {
        FileUploadUtil fileUploadUtil=new FileUploadUtil();
        String fileName= fileUploadUtil.uploadUserBackground(file);
        String username=JwtUtil.parseJWT(request.getHeader("Auth")).getSubject();
        int iid=userMapper.queryUser(username).get(0).getUid();
        int i = userMapper.updateUserBackgroundImage(fileName, iid);
        return i!=0?Result.update_background_success():Result.update_background_error();
    }

    @Override
    public Result userTitleHeadChange(MultipartFile file, HttpServletRequest request) {
        FileUploadUtil fileUploadUtil=new FileUploadUtil();
        String fileName= fileUploadUtil.uploadUserTitleImage(file);
        String username=JwtUtil.parseJWT(request.getHeader("Auth")).getSubject();
        int iid=userMapper.queryUser(username).get(0).getUid();
        int i = userMapper.updateUserTitleHeadImage(fileName, iid);
        return i!=0?Result.update_titlehead_success():Result.update_titlehead_error();
    }

    @Override
    public Result userSelfInfoChange(UserInfo userInfo) {
        int i = userMapper.updateUserSelfInfo(userInfo.getIid(), userInfo.getNickname(), userInfo.getSelf(), userInfo.getSex(), userInfo.getBirthday(), userInfo.getEmail());
        return i!=0?Result.update_user_info_success():Result.update_user_info_error();
    }

    @Override
    public boolean followUser(int uid, int fan) {
        if (userMapper.queryFollow(uid,fan).isEmpty()){
            return userMapper.insertIntoFollow(uid, fan)!=0;
        }else {
            return userMapper.deleteFromFollow(uid, fan)!=0;
        }
    }

    @Override
    public boolean getFollowStatue(int uid, int fan) {
        return userMapper.queryFollow(uid, fan).isEmpty();
    }
}
