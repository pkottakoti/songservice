package com.songservice.songservice.controllers;

import com.songservice.songservice.models.Song;
import com.songservice.songservice.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SongController {

    private final SongService songService;

    @Value("${songLimit}")
    private int songLimit;

    @Autowired
    public SongController(SongService songService){
        this.songService = songService;
    }

    @PostMapping("/song")
    public Song postSong(@RequestBody Song song){
        return songService.saveSong(song);

    }

    @GetMapping("/songs/title/{title}")
    public List<Song> getSongsByTitle(@PathVariable String title){
        System.out.println("song limit:"+songLimit);
        return songService.findSongByTitle(title);
    }
    @GetMapping("/songs/artist/{artist}")
    public List<Song> getSongsByArtist(@PathVariable String artist){
        return songService.findSongsByArtist(artist);
    }


}
