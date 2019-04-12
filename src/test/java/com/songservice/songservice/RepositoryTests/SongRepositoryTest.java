package com.songservice.songservice.RepositoryTests;


import com.songservice.songservice.TestUtils.TestSongs;
import com.songservice.songservice.models.Song;
import com.songservice.songservice.repositories.SongRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
//@AutoConfigureTestDatabase(replace= Replace.NONE)
public class SongRepositoryTest {


    @Autowired
    private SongRepository songRepository;

    private List<Song> testSongs;
    @Before
    public void setUp(){
        testSongs=new ArrayList<>();
        for(Song song:TestSongs.getSongs()){
           Song songWithId=songRepository.save(song);
            testSongs.add(songWithId);
        }
    }

    @After
    public void tearDown(){
        for(Song song:testSongs){
            songRepository.delete(song);
        }
    }


    @Test
    public void repoSavesInDB() throws Exception {
        Song song = TestSongs.getSongs().get(0);

        Song songSaved = songRepository.save(song);
        Song foundSong = songRepository.findById(songSaved.getId()).orElse(null);
        assertThat(foundSong.getTitle()).isEqualTo(song.getTitle());
    }

   /* @Test
    public void findSongByName_returnsSongsWithThatName(){
        String title="Africa";

        int size = songRepository.findByTitle(title).size();
        assertThat(size==2);

    }*/

   @Test
   public void repoSearchBysByArtist() throws Exception{
       List<Song> songsByArtist=songRepository.findByArtist("Michael Jackson");
       for(Song song:songsByArtist){
           assertThat(song.getArtist()).isEqualTo("Michael Jackson");
       }
   }
}