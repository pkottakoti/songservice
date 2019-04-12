package com.songservice.songservice.repositories;


import com.songservice.songservice.models.Song;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface SongRepository extends MongoRepository<Song,String> {
    List<Song> findByTitle(String title);
    List<Song> findByArtist(String artist);
}
