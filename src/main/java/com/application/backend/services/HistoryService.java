package com.application.backend.services;

import com.application.backend.entity.table.History;
import com.application.backend.entity.Result;

import java.util.List;

public interface HistoryService {
    List<History> getAllHistory();
    List<History> getHistoryByUid(String token);
    List<History> getHistoryByPid(int pid);
    List<History> getHistoryByDate(long date);
    List<History> getHistoryByUidViewtime(String token,long viewtime);
    Result createHistory(History history);
    Result deleteHistory(History history);
    Result deleteUserAllHistory(String token);
}
