package com.application.backend.services.impl;

import com.application.backend.entity.Artworks;
import com.application.backend.entity.Creative;
import com.application.backend.entity.Favorite;
import com.application.backend.entity.Result;
import com.application.backend.mapper.ArtWorkMapper;
import com.application.backend.mapper.UserMapper;
import com.application.backend.services.ArtworkService;
import com.application.backend.utils.FileUploadUtil;
import com.application.backend.utils.JwtUtil;
import com.application.backend.utils.ResUrl;
import com.application.backend.utils.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

@Slf4j
@Service
public class ArtworkServiceImpl implements ArtworkService {
    @Autowired
    protected ArtWorkMapper artWorkMapper;
    @Autowired
    protected UserMapper userMapper;
    @Value(("${web.picture-data-res-path}"))
    protected String artWorksRes;
    @Value(("${web.picture-data-res-path-compressed}"))
    protected String resCompressed;
    /*private String getJarFilePath() {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        return jarFile.getParentFile().toString();
    }*/

    /***
     * 查询VuePictures里所有插画
     * @return 所有插画的集合
     */
    @Override
    public List<Artworks> getAllArtworks() {
        List<Artworks> list=artWorkMapper.queryAllArtworks();
        for (Artworks artworks:list){
            artworks.setPicture(artWorksRes+artworks.getPicture());
        }
        return list;
    }

    /***
     * 分页查询VuePictures里所有插画
     * @param token 用户token
     * @param pageNum 页号
     * @return 封装成IPage的插画对象，每页10张插画
     */
    @Override
    public IPage<Artworks> getAllArtworksPaged(String token,int pageNum) {
        Page<Artworks> page=new Page<>(pageNum,10);
        List<Favorite> favoriteList = getFavorite(token);
        QueryWrapper<Artworks> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("del",0);
        IPage<Artworks> iPage=artWorkMapper.selectPage(page,queryWrapper);
        List<Artworks> list=iPage.getRecords();
        for (Artworks artworks:list){
            artworks.setPicture(resCompressed+artworks.getPicture());
            for (Favorite favorite:favoriteList){
                if (artworks.getPid()==favorite.getPid()){
                    artworks.setLiked(true);
                    break;
                }else {
                    artworks.setLiked(false);
                }
            }
        }
        return iPage;
    }

    @Override
    public IPage<Artworks> getSubmitArtworksPaged(String token, int pageNum) {
        int uid=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        Page<Artworks> page=new Page<>(pageNum,9);
        QueryWrapper<Artworks> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uid",uid);
        IPage<Artworks> iPage=artWorkMapper.selectPage(page,queryWrapper);
        List<Artworks> artworks=iPage.getRecords();
        for (Artworks artwork:artworks){
            if (artwork.getDel()!=0){
                artworks.remove(artwork);
            }
            artwork.setPicture(resCompressed+artwork.getPicture());
        }
        return iPage;
    }

    /***
     * 随机获取VuePictures里指定数量的插画
     * @param num 获取插画的数量
     * @return 获取插画组成的集合
     */
    @Override
    public List<Artworks> getRandArtworks(int num) {
        List<Artworks> list=artWorkMapper.queryRandArtworks(num);
        for (Artworks artworks:list){
            artworks.setPicture(artWorksRes+artworks.getPicture());
        }
        return list;
    }

    /***
     * 获取VuePictures里指定数量的热门插画
     * @param num 获取插画的数量
     * @return 获取插画组成的集合
     */
    @Override
    public List<Artworks> getHotArtworks(int num) {
        List<Artworks> list=artWorkMapper.queryMostLikedArtworks(num);
        for (Artworks artworks:list){
            artworks.setPicture(artWorksRes+artworks.getPicture());
        }
        return list;
    }

    /***
     * 根据插画id查询插画
     * @param id 插画的id
     * @return 获取插画组成的集合
     */
    @Override
    public List<Artworks> getArtworksById(int id) {
        List<Artworks> list=artWorkMapper.queryArtworksByPid(id);
        for (Artworks artworks:list){
            artworks.setPicture(artWorksRes+artworks.getPicture());
        }
        return list;
    }

    /***
     * 更新插画的浏览量（加一）
     * @param pid 插画的id
     * @return 操作是否执行成功
     */

    @Override
    public boolean artworksViewed(int pid) {
        int i = artWorkMapper.updateArtworksView(pid);
        return i != 0;
    }

    /***
     * 下载插画
     * @param pid 插画的id
     * @return 指定插画流
     * @throws IOException 可能异常：插画不存在
     */
    @Override
    public byte[] downloadArtworks(int pid) throws IOException {
        List<Artworks> list=artWorkMapper.queryArtworksByPid(pid);
        Artworks artworks=list.get(0);
        String format=artworks.getPicture().substring(artworks.getPicture().length()-3);
        artworks.setPicture(ResUrl.USER_SUBMIT_ARTWORK_PATH+"\\"+artworks.getPicture());
        BufferedImage image=ImageIO.read(new File(artworks.getPicture()));
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        ImageIO.write(image,format,os);
        return os.toByteArray();
    }

    /***
     * 更新插画的最后浏览时间
     * @param pid 指定插画的id
     * @return 操作是否执行成功
     */
    @Override
    public boolean updateArtworksViewTime(int pid) {
        return artWorkMapper.updateArtworksLastViewTime(pid,System.currentTimeMillis())!=0;
    }

    /***
     * 收藏插画
     * @param token 用户token
     * @param pid 插画id
     * @return 操作是否执行成功
     */

    @Override
    public boolean artworksFavorite(String token, int pid) {
        int uid = userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        List<Favorite> list=artWorkMapper.queryFavoriteByUidPid(uid,pid);
        if (list.isEmpty()){
            int i = artWorkMapper.updateArtworksLikeNumPlus(pid);
            return artWorkMapper.insertIntoFavorite(uid,pid,System.currentTimeMillis())!=0;

        }else {
            int i = artWorkMapper.updateArtworksLikeNumReduce(pid);
            return artWorkMapper.deleteFavorite(list.get(0).getUid(),pid)!=0;
        }
    }

    /***
     * 获取用户对某插画收藏状态
     * @param token 用户token
     * @param pid 插画id
     * @return 是否收藏
     */
    @Override
    public boolean artworksFavoriteStatue(String token, int pid) {
        int uid = userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        List<Favorite> list=artWorkMapper.queryFavoriteByUidPid(uid, pid);
        return list.isEmpty();
    }

    /***
     * 获取用户收藏插画
     * @param token 用户token
     * @return 用户收藏插画组成的集合
     */

    @Override
    public List<Favorite> getFavorite(String token) {
        int uid = userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        return artWorkMapper.queryFavoriteByUid(uid);
    }

    /***
     * 用户投稿作品是否自我收藏
     * @param uid 用户id
     * @return 用户投稿作品组成的集合（添加编辑是否收藏的属性liked）
     */

    @Override
    public List<Artworks> getUserArtworks(int uid) {
        List<Artworks> list = artWorkMapper.queryArtworksByUid(uid);
        List<Favorite> list1 = artWorkMapper.queryFavoriteByUid(uid);
        for (Artworks artworks:list){
            artworks.setPicture(resCompressed+artworks.getPicture());
            for (Favorite favorite:list1){
                if (artworks.getPid()==favorite.getPid()){
                    artworks.setLiked(true);
                    break;
                }else {
                    artworks.setLiked(false);
                }
            }
        }
        return list;
    }

    @Override
    public List<Artworks> getFavoriteArtworks(int uid) {
        List<Artworks> list = artWorkMapper.queryFavoriteArtworks(uid);
        List<Favorite> list1=artWorkMapper.queryFavoriteByUid(uid);
        for (Artworks artworks:list){
            artworks.setPicture(resCompressed+artworks.getPicture());
            for (Favorite favorite:list1){
                if (artworks.getPid()==favorite.getPid()){
                    artworks.setLiked(true);
                    break;
                }else {
                    artworks.setLiked(false);
                }
            }
        }
        return list;
    }

    @Override
    public boolean submitArtwork(MultipartFile file, String username, String subtitle, String introduce) {
        FileUploadUtil fileUploadUtil=new FileUploadUtil();
        int uid = userMapper.queryUser(JwtUtil.parseJWT(username).getSubject()).get(0).getUid();
        String picture = fileUploadUtil.uploadArtworkImage(file).getMessage();
        return artWorkMapper.insertIntoArtworks(uid, picture, subtitle, System.currentTimeMillis(), introduce)!=0;
    }

    @Override
    public Creative getUserIdeaData(String token) {
        int uid=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        Artworks lastSubmit=artWorkMapper.queryLastSubmitArtworks(uid);
        Artworks lastView=artWorkMapper.queryLastViewArtworks(uid);
        Favorite lastFavorite=artWorkMapper.queryLastFavoriteArtworks(uid);
        Integer views=artWorkMapper.queryUserViewNum(uid);
        int fans=userMapper.queryFansNumber(uid);
        if (lastSubmit==null||lastView==null||lastFavorite==null){
            return new Creative(new Artworks(),new Artworks(),new Favorite(),fans,0);
        }else {
            lastSubmit.setPicture(resCompressed+lastSubmit.getPicture());
            lastView.setPicture(resCompressed+lastView.getPicture());
            return new Creative(lastSubmit,lastView,lastFavorite,fans,views);
        }
    }

    @Override
    public Result delSubmit(String token, int id) {
        int uid=userMapper.queryUser(JwtUtil.parseJWT(token).getSubject()).get(0).getUid();
        int res=artWorkMapper.deleteSubmit(uid,id);
        if (res!=0){
            return Result.delete(ResultCode.SUCCESS,"删除成功！");
        }else {
            return Result.delete(ResultCode.ERROR,"删除失败！");
        }
    }
}
