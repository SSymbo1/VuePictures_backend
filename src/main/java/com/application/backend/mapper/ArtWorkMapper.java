package com.application.backend.mapper;

import com.application.backend.entity.Artworks;
import com.application.backend.entity.Favorite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ArtWorkMapper extends BaseMapper<Artworks> {
    @Select("SELECT * FROM artworks ORDER BY RAND()")
    List<Artworks> queryAllArtworks();
    @Select("SELECT * FROM artworks ORDER BY RAND() LIMIT #{getNUm}")
    List<Artworks> queryRandArtworks(int getNum);
    @Select("SELECT * FROM artworks ORDER BY likenum DESC LIMIT #{getNum}")
    List<Artworks> queryMostLikedArtworks(int getNum);
    @Select("SELECT * FROM artworks WHERE pid=#{pid}")
    List<Artworks> queryArtworksByPid(int pid);
    @Select("SELECT * FROM artworks WHERE uid=#{uid} ORDER BY createtime DESC LiMIT 1")
    Artworks queryLastSubmitArtworks(int uid);
    @Select("SELECT * FROM artworks WHERE uid=#{uid} ORDER BY lastviewtime DESC LIMIT 1")
    Artworks queryLastViewArtworks(int uid);
    @Select("SELECT favorite.uid,favorite.pid,favoritetime FROM favorite,artworks WHERE favorite.pid=artworks.pid AND artworks.uid=#{uid} ORDER BY favorite.favoritetime DESC LIMIT 1")
    Favorite queryLastFavoriteArtworks(int uid);
    @Select("SELECT SUM(view) FROM artworks WHERE uid=#{uid}")
    Integer queryUserViewNum(int uid);
    @Update("UPDATE artworks SET view=view+1 WHERE pid=#{pid}")
    int updateArtworksView(int pid);
    @Update("UPDATE artworks SET lastviewtime=#{lastviewtime} WHERE pid=#{pid}")
    int updateArtworksLastViewTime(int pid,long lastviewtime);
    @Insert("INSERT INTO favorite VALUE (#{uid},#{pid},#{favoritetime})")
    int insertIntoFavorite(int uid,int pid,long favoritetime);
    @Delete("DELETE FROM favorite WHERE uid=#{uid} AND pid=#{pid}")
    int deleteFavorite(int uid,int pid);
    @Select("SELECT * FROM favorite WHERE uid=#{uid} and pid=#{pid}")
    List<Favorite> queryFavoriteByUidPid(int uid,int pid);
    @Update("UPDATE artworks set likenum=likenum+1 WHERE pid=#{pid}")
    int updateArtworksLikeNumPlus(int pid);
    @Update("UPDATE artworks set likenum=likenum-1 WHERE pid=#{pid}")
    int updateArtworksLikeNumReduce(int pid);
    @Select("SELECT * FROM favorite WHERE uid=#{uid}")
    List<Favorite> queryFavoriteByUid(int uid);
    @Select("SELECT * FROM favorite WHERE pid=#{pid}")
    List<Favorite> queryFavoriteByPid(int pid);
    @Select("SELECT * FROM artworks WHERE uid=#{uid}")
    List<Artworks> queryArtworksByUid(int uid);
    @Select("SELECT artworks.pid,artworks.uid,artworks.picture,subtitle,likenum,createtime,introduce,view,lastviewtime FROM artworks,favorite WHERE favorite.pid=artworks.pid AND favorite.uid=#{uid}")
    List<Artworks> queryFavoriteArtworks(int uid);
    @Insert("INSERT INTO artworks (uid,picture,subtitle,createtime,introduce) VALUE (#{uid},#{picture},#{subtitle},#{createtime},#{introduce})")
    int insertIntoArtworks(int uid,String picture,String subtitle,long createtime,String introduce);
}
