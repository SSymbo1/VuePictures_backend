package com.application.backend.services.impl;

import com.application.backend.entity.table.Block;
import com.application.backend.entity.Result;
import com.application.backend.mapper.BlockMapper;
import com.application.backend.mapper.UserMapper;
import com.application.backend.services.BlockService;
import com.application.backend.utils.JwtUtil;
import com.application.backend.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BlockServiceImpl implements BlockService {
    @Autowired
    private BlockMapper blockMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public Result isUserBlocked(String token, int uid) {
        int ban_uid=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        List<Block> bans=blockMapper.queryUserBlocked(ban_uid);
        if (ban_uid==uid){
            return Result.common(false, ResultCode.SUCCESS,"自己访问了自己的个人资料界面").data("ban_uid",ban_uid);
        }
        for (Block ban:bans){
            if (ban.getBan()==uid){
                return Result.common(true, ResultCode.SUCCESS,"您已被该用户屏蔽！").data("ban_uid",ban_uid);
            }
        }
        return Result.common(false, ResultCode.SUCCESS,"没有被该用户屏蔽！").data("ban_uid",ban_uid);
    }

    @Override
    public Result authUserBlocked(String token, int uid) {
        int ban_uid=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        List<Block> bans=blockMapper.queryBlockedStatue(uid,ban_uid);
        if (bans.isEmpty()){
            return Result.common(false, ResultCode.SUCCESS,"没有被该用户屏蔽！");
        }else {
            return Result.common(true, ResultCode.SUCCESS,"您已被该用户屏蔽，所以无法查看其作品和收藏！");
        }
    }

    @Override
    public Result blockUser(String token, int uid) {
        Result result=isUserBlocked(token,uid);
        int ban_uid= (int) result.getData().get("ban_uid");
        if (result.getSuccess()){
            int res=blockMapper.deleteUserBlock(ban_uid,uid);
            if (res!=0){
                return Result.common(true, ResultCode.SUCCESS,"已从黑名单移除！");
            }else {
                return Result.common(false,ResultCode.ERROR,"移除黑名单失败！");
            }
        }else {
            int res=blockMapper.insertIntoBlock(ban_uid,uid,System.currentTimeMillis());
            if (res!=0){
                return Result.common(true,ResultCode.SUCCESS,"已添加至黑名单！");
            }else {
                return Result.common(false,ResultCode.ERROR,"添加至黑名单失败！");
            }
        }
    }

    @Override
    public List<Integer> getUserBlocker(String token) {
        int ban=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        return blockMapper.queryBlocker(ban);
    }
}
