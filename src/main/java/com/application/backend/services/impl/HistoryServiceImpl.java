package com.application.backend.services.impl;

import com.application.backend.entity.Favorite;
import com.application.backend.entity.History;
import com.application.backend.entity.Result;
import com.application.backend.mapper.ArtWorkMapper;
import com.application.backend.mapper.HistoryMapper;
import com.application.backend.mapper.UserMapper;
import com.application.backend.services.HistoryService;
import com.application.backend.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private HistoryMapper historyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArtWorkMapper artWorkMapper;
    @Value(("${web.picture-data-res-path-compressed}"))
    private String resCompressed;
    @Override
    public List<History> getAllHistory() {
        return historyMapper.queryAllHistory();
    }

    @Override
    public List<History> getHistoryByUid(String token) {
        int uid=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        List<History> histories=historyMapper.queryHistoryByUid(uid);
        List<Favorite> favorites=artWorkMapper.queryFavoriteByUid(uid);
        for (History history:histories){
            history.setArtworks(artWorkMapper.queryArtworksByPid(history.getPid()).get(0));
            history.getArtworks().setPicture(resCompressed+history.getArtworks().getPicture());
            for (Favorite favorite:favorites){
                if (favorite.getPid()==history.getPid()){
                    history.getArtworks().setLiked(true);
                }
            }
        }
        return histories;
    }

    @Override
    public List<History> getHistoryByPid(int pid) {
        return historyMapper.queryHistoryByPid(pid);
    }

    @Override
    public List<History> getHistoryByDate(long date) {
        return historyMapper.queryHistoryByDate(date);
    }

    @Override
    public List<History> getHistoryByUidViewtime(String token, long viewtime) {
        List<History> histories=this.getHistoryByUid(token);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(viewtime);
        histories.removeIf(history -> !sdf.format(new Date(history.getViewtime())).equals(sdf.format(date)));
        return histories;
    }

    @Override
    public Result createHistory(History history) {
        String username= JwtUtil.parseJWT(history.getToken()).getSubject();
        int uid=userMapper.queryUser(username).get(0).getUid();
        List<History> list=historyMapper.queryHistoryByPidUid(uid,history.getPid());
        if (list.isEmpty()){
            int re = historyMapper.insertIntoHistory(uid, history.getPid(), System.currentTimeMillis());
            if (re!=0){
                return Result.history_add();
            }else {
                return Result.history_error();
            }
        }else {
            int re=historyMapper.updateViewTime(uid, history.getPid(), System.currentTimeMillis());
            if (re!=0){
                return Result.history_update();
            }
        }
        return Result.history_error();
    }

    @Override
    public Result deleteHistory(History history) {
        int uid=userMapper.queryUser(JwtUtil.parseJWT(history.getToken()).getSubject()).get(0).getUid();
        if (historyMapper.deleteHistory(uid,history.getPid())!=0){
            return Result.history_delete();
        }else {
            return Result.history_error();
        }
    }

    @Override
    public Result deleteUserAllHistory(String token) {
        int uid=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        if(historyMapper.deleteUserHistory(uid)!=0){
            return Result.history_delete();
        }else {
            return Result.history_error();
        }
    }
}
