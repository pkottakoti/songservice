package com.songservice.songservice.services;


import com.songservice.songservice.models.Song;
import com.songservice.songservice.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    private SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository){
        this.songRepository = songRepository;
    }

    public Song saveSong(Song song) {
        return songRepository.save(song);
    }

    public List<Song> findSongByTitle(String title){
        return songRepository.findByTitle(title);
    }
    public List<Song> findSongsByArtist(String artist){
        return songRepository.findByArtist(artist);
    }

}
