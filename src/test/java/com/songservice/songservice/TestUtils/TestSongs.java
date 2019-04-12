package com.songservice.songservice.TestUtils;

import com.songservice.songservice.models.Song;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TestSongs {

    public static List<Song> getSongs(){
        List<Song> songs = new ArrayList<Song>();
        songs.add(new Song("Africa","Weezer", Duration.ofSeconds(243)));
        songs.add(new Song("Africa","Toto", Duration.ofSeconds(274)));
        songs.add(new Song("Take On Me","a-Ha", Duration.ofSeconds(227)));
        songs.add(new Song("Billie Jean","Michael Jackson", Duration.ofSeconds(210)));
        songs.add(new Song("I Would Do Anything for Love","Meatloaf",Duration.ofSeconds(756)));
        songs.add(new Song("Closer","The Chainsmokers",Duration.ofSeconds(244)));
        songs.add(new Song("something like this","The Chainsmokers",Duration.ofSeconds(244)));
        return songs;
    }
}

