package com.application.backend.mapper;

import com.application.backend.entity.Block;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlockMapper {
    @Select("SELECT * FROM block WHERE uid=#{uid}")
    List<Block> queryUserBlocked(int uid);
    @Select("SELECT * FROM block WHERE uid=#{uid} AND ban=#{ban}")
    List<Block> queryBlockedStatue(int uid,int ban);
    @Select("SELECT ban FROM block WHERE uid=#{ban}")
    List<Integer> queryBlocker(int ban);
    @Insert("INSERT INTO block VALUE (#{uid},#{ban},#{banning_time})")
    int insertIntoBlock(int uid,int ban,long banning_time);
    @Delete("DELETE FROM block WHERE uid=#{uid} AND ban=#{ban}")
    int deleteUserBlock(int uid,int ban);

}
