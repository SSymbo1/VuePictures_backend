package com.application.backend.services;

import com.application.backend.entity.Result;

import java.util.List;

public interface BlockService {
    Result isUserBlocked(String token,int uid);
    Result authUserBlocked(String token,int uid);
    Result blockUser(String token,int uid);
    List<Integer> getUserBlocker(String token);
}
