package com.application.backend.ws;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.application.backend.entity.Message;
import com.application.backend.entity.Result;
import com.application.backend.entity.table.User;
import com.application.backend.entity.table.UserInfo;
import com.application.backend.mapper.UserInfoMapper;
import com.application.backend.mapper.UserMapper;
import com.application.backend.services.MessageService;
import com.application.backend.services.impl.MessageServiceImpl;
import com.application.backend.utils.ApplicationContextProvider;
import com.application.backend.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint("/chat_ws/{token}")
@Component
public class WebSocketServer {
    private final UserMapper userMapper = ApplicationContextProvider.getBean(UserMapper.class);
    private final UserInfoMapper userInfoMapper=ApplicationContextProvider.getBean(UserInfoMapper.class);
    private final MessageService messageService= ApplicationContextProvider.getBean(MessageServiceImpl.class);
    private static final Map<Integer,Session> sessionMap = new ConcurrentHashMap<>();
    private final static String photoRes="http://localhost:9090/resUser/";
    private final static String photoBackRes="http://localhost:9090/resBk/";
    @OnOpen
    public void onOpen(Session session,@PathParam("token") String token){
        User user=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0);
        sessionMap.put(user.getUid(),session);
        log.info("用户:"+user.getUsername()+",uid:"+user.getUid()+",已建立ws链接!"+"当前链接人数为:"+sessionMap.size());
        sendAllMessage(setUserList());
    }
    @OnClose
    public void onClose(@PathParam("token") String token){
        User user=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0);
        sessionMap.remove(user.getUid());
        log.info("用户:"+user.getUsername()+",uid:"+user.getUid()+",已断开ws链接！"+"当前链接人数为:"+sessionMap.size());
        sendAllMessage(setUserList());
    }
    @OnError
    public void onError(Session session,Throwable error){
        log.warn("WebSocket服务发生错误：");
        error.printStackTrace();
    }
    @OnMessage
    public void onMessage(String message){
        Message msg=JSON.parseObject(message,Message.class);
        if (StringUtils.isEmpty(String.valueOf(msg.getTo()))||msg.getTo()==null){
            sendAllMessage(JSON.toJSONString(msg));
        }else {
            Session sessionTo=sessionMap.get(msg.getTo());
            sendMessage(message,sessionTo);
        }

    }
    public void sendMessage(String message,Session toSession){
        com.application.backend.entity.table.Message msg=new com.application.backend.entity.table.Message();
        JSONObject json=JSONObject.parseObject(message);
        msg.setSend(Integer.parseInt(json.getString("from")));
        msg.setAccept(Integer.parseInt(json.getString("to")));
        msg.setMessage(json.getString("message"));
        Result result = messageService.historyMessage(msg);
        try {
            toSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.warn("发送信息失败！");
        }
    }
    public void sendAllMessage(String message){
        for(Session session:sessionMap.values()){
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.warn("发送全体信息失败！");
                e.printStackTrace();
            }
        }
    }
    private String setUserList(){
        Message message=new Message();
        ArrayList<Integer> onlineUsers = new ArrayList<>(sessionMap.keySet());
        ArrayList<UserInfo> userInfos=new ArrayList<>();
        for (Integer integer:onlineUsers){
            userInfos.add(userInfoMapper.queryUserInfoByUid(integer).get(0));
        }
        for (UserInfo userInfo:userInfos){
            userInfo.setUserimage(photoRes+userInfo.getUserimage());
            userInfo.setBackground(photoBackRes+userInfo.getBackground());
        }
        message.setUserInfos(userInfos);
        return JSONUtil.toJsonPrettyStr(message);
    }
}
