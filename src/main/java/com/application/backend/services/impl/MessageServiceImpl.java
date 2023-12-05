package com.application.backend.services.impl;

import com.application.backend.entity.Result;
import com.application.backend.entity.table.Message;
import com.application.backend.mapper.MessageMapper;
import com.application.backend.mapper.UserMapper;
import com.application.backend.services.MessageService;
import com.application.backend.utils.JwtUtil;
import com.application.backend.utils.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public Result historyMessage(Message message) {
        int res=messageMapper.insertHistoryMessage(message.getSend(),message.getAccept(),message.getMessage(),System.currentTimeMillis());
        if (res!=0){
            return Result.common(true, ResultCode.SUCCESS,"save message history successfully");
        }
        return Result.common(false,ResultCode.ERROR,"save message history failed");
    }

    @Override
    public List<Message> getHistory(String token, int accept) {
        int send=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        return messageMapper.queryHistoryMessage(send, accept);
    }
}
