package com.application.backend.mapper;

import com.application.backend.entity.History;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface HistoryMapper extends BaseMapper<History> {
    @Select("SELECT * FROM history,artworks WHERE history.pid=artworks.pid AND artworks.del=0")
    List<History> queryAllHistory();
    @Select("SELECT * FROM history,artworks WHERE history.pid=artworks.pid AND history.uid=#{uid} AND history.pid=#{pid} AND artworks.del=0 ORDER BY viewtime DESC")
    List<History> queryHistoryByPidUid(int uid,int pid);
    @Select("SELECT * FROM history,artworks WHERE history.pid=artworks.pid AND history.uid=#{uid} AND history.viewtime=#{viewtime} AND artworks.del=0")
    List<History> queryHistoryByUidViewtime(int uid,long viewtime);
    @Select("SELECT * FROM history,artworks WHERE history.pid=artworks.pid AND history.uid=#{uid} AND artworks.del=0 ORDER BY viewtime DESC")
    List<History> queryHistoryByUid(int uid);
    @Select("SELECT * FROM history,artworks WHERE history.pid=artworks.pid AND history.pid=#{pid} AND artworks.del=0")
    List<History> queryHistoryByPid(int pid);
    @Select("SELECT * FROM history,artworks WHERE history.pid=artworks.pid AND history.viewtime=#{viewtime} AND artworks.del=0")
    List<History> queryHistoryByDate(long viewtime);
    @Insert("INSERT INTO history VALUE (#{uid},#{pid},#{viewtime})")
    int insertIntoHistory(int uid,int pid,long viewtime);
    @Update("UPDATE history SET viewtime=#{viewtime} WHERE uid=#{uid} AND pid=#{pid}")
    int updateViewTime(int uid,int pid,long viewtime);
    @Delete("DELETE FROM history WHERE uid=#{uid} AND pid=#{pid}")
    int deleteHistory(int uid,int pid);
    @Delete("DELETE FROM history WHERE uid=#{uid}")
    int deleteUserHistory(int uid);


}
