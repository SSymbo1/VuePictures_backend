package com.application.backend.controller;

import com.application.backend.entity.Artworks;
import com.application.backend.entity.UserInfo;
import com.application.backend.services.SearchService;
import com.application.backend.services.impl.SearchServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@CrossOrigin
@RestController
public class SearchController {
    @Autowired
    private final SearchService searchService=new SearchServiceImpl();
    @GetMapping("/search_artworks") //查询插画接口，提供用户token，搜索关键字，页数，返回分页后的查询结果
    public IPage<Artworks> getSearchedArtworks(String token, String search, int page){
        return searchService.searchArtworks(token,search,page);
    }
    @GetMapping("search_user") //查询用户接口
    public IPage<UserInfo> getSearchedUser(String search, int page){
        return searchService.searchUser(search, page);
    }
    @GetMapping("/search_submit")
    public IPage<Artworks> getSearchedSubmit(String token,String keyword,int page){
        return searchService.artworksManagerSearcher(token,keyword,page);
    }
    @GetMapping("/search_fans")
    public IPage<UserInfo> getSearchedFans(String token,String keyword,int page){
        return searchService.fansManagerSearcher(token, keyword, page);
    }
}
