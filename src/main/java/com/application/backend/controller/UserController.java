package com.application.backend.controller;

import com.application.backend.entity.Result;
import com.application.backend.entity.User;
import com.application.backend.entity.UserInfo;
import com.application.backend.services.UserService;
import com.application.backend.services.impl.UserServiceImpl;
import com.application.backend.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private final UserService userService=new UserServiceImpl();
    @PostMapping ("/login") //登录接口，提供User对象，返回Result封装的结果集
    public Result login(@RequestBody User user){
        return userService.userLogin(user);
    }
    @PostMapping("/register") //注册接口，提供User对象，返回Result封装的结果集
    public Result register(@RequestBody User user){
        return userService.userRegister(user);
    }
    @GetMapping("/user") //当前用户名接口，提供token，返回username
    public String getUser(String token){
        return JwtUtil.parseJWT(token).getSubject();
    }
    @GetMapping("/fans_info") //用户关注，粉丝接口，提供token，返回粉丝数，关注数组成的集合
    public List<Integer> getFansInfo(String token){
        return userService.getUserFansInfo(token);
    }
    @GetMapping("/user_info") //用户详细信息接口，提供token，返回用户详细信息集合
    public List<UserInfo> getUserInfo(String token){
        return userService.getUserInfo(token);
    }
    @PostMapping("/uploadBk") //更新用户资料背景图片接口，提供file对象，请求头里添加“Auth”名称的token，返回Result封装的结果集
    public Result setUserBackground(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        return userService.userBackgroundChange(file,request);
    }
    @PostMapping("/uploadTH") //更新用户头像接口，提供file对象，请求头里添加“Auth”名称的token，返回Result封装的结果集
    public Result setUserTitleHead(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        return userService.userTitleHeadChange(file,request);
    }
    @PostMapping("/uploadSelf") //更新用户详细信息接口，提供UserInfo对象，返回Result封装的结果集
    public Result setUserSelf(@RequestBody UserInfo userinfo){
        return userService.userSelfInfoChange(userinfo);
    }
    @GetMapping("/user_info_id") //获取用户详细信息接口，提供uid（用户id）,返回UserInfo封装的用户详细信息
    public List<UserInfo> getUserInfoById(int uid){
        return userService.getUserInfoById(uid);
    }
    @GetMapping("/follow") //关注用户（取消关注用户）接口，提供关注者uid，粉丝uid（fan），返回操作结果boolean
    public boolean followUser(int uid,int fan){
        return userService.followUser(uid,fan);
    }
    @GetMapping("/followStatue") //查询关注情况接口，提供关注者uid，粉丝uid（fan），返回粉丝是否关注关注者boolean
    public boolean followStatue(int uid,int fan){
        return userService.getFollowStatue(uid, fan);
    }
    @GetMapping("/all_user_follower") //获取用户关注者集合，提供token，返回关注者
    public List<Integer> getAllUserFollower(String token){
        return userService.getFollowedUser(token);
    }
}
