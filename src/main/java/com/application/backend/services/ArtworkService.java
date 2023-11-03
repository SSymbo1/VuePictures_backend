package com.application.backend.services;

import com.application.backend.entity.Artworks;
import com.application.backend.entity.Creative;
import com.application.backend.entity.Favorite;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ArtworkService {
    List<Artworks> getAllArtworks();
    IPage getAllArtworksPaged(String token,int pageNum);
    List<Artworks> getRandArtworks(int num);
    List<Artworks> getHotArtworks(int num);
    List<Artworks> getArtworksById(int id);
    List<Favorite> getFavorite(String token);
    Creative getUserIdeaData(String token);
    boolean artworksViewed(int pid);
    byte[] downloadArtworks(int pid) throws IOException;
    boolean updateArtworksViewTime(int pid);
    boolean artworksFavorite(String token,int pid);
    boolean artworksFavoriteStatue(String token,int pid);
    List<Artworks> getUserArtworks(int uid);
    List<Artworks> getFavoriteArtworks(int uid);
    boolean submitArtwork(MultipartFile file,String username,String subtitle,String introduce);
}
