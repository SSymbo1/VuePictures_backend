package com.application.backend.services;

import com.application.backend.entity.Result;
import com.application.backend.entity.table.Message;

import java.util.List;

public interface MessageService {
    Result historyMessage(Message message);
    List<Message> getHistory(String token,int accept);
}
