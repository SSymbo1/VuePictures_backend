package com.application.backend.services;

import com.application.backend.entity.Artworks;
import com.application.backend.entity.UserInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface SearchService {
    IPage<Artworks> searchArtworks(String token, String search, int pageNum);
    IPage<UserInfo> searchUser(String search, int pageNum);
    IPage<Artworks> artworksManagerSearcher(String token,String keyword,int pageNum);
}
