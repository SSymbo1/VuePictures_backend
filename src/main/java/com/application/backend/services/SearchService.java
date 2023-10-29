package com.application.backend.services;

import com.baomidou.mybatisplus.core.metadata.IPage;

public interface SearchService {
    IPage searchArtworks(String token,String search,int pageNum);
    IPage searchUser(String search,int pageNum);
}
