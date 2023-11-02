package com.application.backend.mapper;

import com.application.backend.entity.History;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HistoryMapper extends BaseMapper<History> {
    @Insert("INSERT INTO history VALUE (#{uid},#{pid},#{viewtime})")
    int insertIntoHistory(int uid,int pid,long viewtime);
    @Update("UPDATE history SET viewtime=#{viewtime} WHERE uid=#{uid} AND pid=#{pid}")
    int updateViewTime(int uid,int pid,long viewtime);
    @Delete("DELETE FROM history WHERE uid=#{uid} AND pid=#{pid}")
    int deleteHistory(int uid,int pid);
    @Delete("DELETE FROM history WHERE uid=#{uid}")
    int deleteUserHistory(int uid);
    @Select("SELECT * FROM history")
    List<History> queryAllHistory();
    @Select("SELECT * FROM history WHERE uid=#{uid} AND pid=#{pid} ORDER BY viewtime DESC")
    List<History> queryHistoryByPidUid(int uid,int pid);
    @Select("SELECT * FROM history WHERE uid=#{uid} AND viewtime=#{viewtime}")
    List<History> queryHistoryByUidViewtime(int uid,long viewtime);
    @Select("SELECT * FROM history WHERE uid=#{uid} ORDER BY viewtime DESC")
    List<History> queryHistoryByUid(int uid);
    @Select("SELECT * FROM history WHERE pid=#{pid}")
    List<History> queryHistoryByPid(int pid);
    @Select("SELECT * FROM history WHERE viewtime=#{viewtime}")
    List<History> queryHistoryByDate(long viewtime);

}
