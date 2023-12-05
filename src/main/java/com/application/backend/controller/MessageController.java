package com.application.backend.controller;

import com.application.backend.entity.table.Message;
import com.application.backend.services.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@CrossOrigin
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;
    @GetMapping("/historyMessage")
    List<Message> getHistoryMessage(String token,int accept){
        return messageService.getHistory(token, accept);
    };
}
