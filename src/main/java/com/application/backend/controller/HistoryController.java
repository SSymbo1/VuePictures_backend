package com.application.backend.controller;

import com.application.backend.entity.History;
import com.application.backend.entity.Result;
import com.application.backend.services.HistoryService;
import com.application.backend.services.impl.HistoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class HistoryController {
    @Autowired
    private HistoryService historyService=new HistoryServiceImpl();
    @PostMapping("/addHistory")
    public Result addHistory(@RequestBody History history){
        return historyService.createHistory(history);
    }
    @PostMapping("/deleteHistory")
    public Result deleteHistory(@RequestBody History history){
        return historyService.deleteHistory(history);
    }
    @GetMapping("/deleteUserHistory")
    public Result deleteUserHistory(String token){
        return historyService.deleteUserAllHistory(token);
    }
    @GetMapping("/allHistory")
    public List<History> getAllHistory(){
        return historyService.getAllHistory();
    }
    @GetMapping("/historyUid")
    public List<History> getHistoryByUid(String token){
        return historyService.getHistoryByUid(token);
    }
    @GetMapping("/historyPid")
    public List<History> getHistoryByPid(int pid){
        return historyService.getHistoryByPid(pid);
    }
    @GetMapping("/historyDate")
    public List<History> getHistoryByDate(long viewtime){
        return historyService.getHistoryByDate(viewtime);
    }
    @GetMapping("/historyUidDate")
    public List<History> getHistoryByUidDate(String token,long viewtime){
        return historyService.getHistoryByUidViewtime(token, viewtime);
    }
}
