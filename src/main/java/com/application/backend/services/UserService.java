package com.application.backend.services;

import com.application.backend.entity.Result;
import com.application.backend.entity.User;
import com.application.backend.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    List<User> getUser(String username);
    List<UserInfo> getUserInfo(String token);
    List<UserInfo> getUserInfoById(int uid);
    List<Integer> getUserFansInfo(String token);
    boolean getFollowStatue(int uid,int fan);
    boolean followUser(int uid,int fan);
    Result userRegister(User user);
    Result userLogin(User user);
    String getUserImg(String token);
    Result userBackgroundChange(MultipartFile file, HttpServletRequest request);
    Result userTitleHeadChange(MultipartFile file, HttpServletRequest request);
    Result userSelfInfoChange(UserInfo userInfo);
}
