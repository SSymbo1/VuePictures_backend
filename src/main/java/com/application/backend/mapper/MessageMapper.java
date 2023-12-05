package com.application.backend.mapper;

import com.application.backend.entity.table.Message;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("SELECT * FROM message WHERE (send=#{send} AND accept=#{accept}) OR (send=#{accept} AND accept=#{send}) ORDER BY time")
    List<Message> queryHistoryMessage(int send,int accept);
    @Insert("INSERT INTO message(send, accept, message, time) VALUE (#{send},#{accept},#{message},#{time})")
    int insertHistoryMessage(int send,int accept,String message,long time);
}
