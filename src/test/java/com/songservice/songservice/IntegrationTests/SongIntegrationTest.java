package com.songservice.songservice.IntegrationTests;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.songservice.songservice.TestUtils.TestSongs;
import com.songservice.songservice.models.Song;
import com.songservice.songservice.repositories.SongRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SongIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private SongRepository songRepository;

    private List<Song> testSongs;
    @Before
    public void setUp(){
        testSongs=new ArrayList<>();
        for(Song song:TestSongs.getSongs()){
            ResponseEntity<Song> songResponseEntity = restTemplate.postForEntity("/song", song,Song.class);
            Song songWithId=songResponseEntity.getBody();
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
    public void postingSong_savesTheSong() throws JsonProcessingException {
        //act
        ResponseEntity<Song> response = restTemplate.postForEntity("/song", TestSongs.getSongs().get(1),Song.class);
        Song song=response.getBody();
        Song savedSong=songRepository.findById(song.getId()).orElse(null);
        //assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(savedSong.getId()).isNotNull();
        assertThat(savedSong.getTitle()).isEqualTo(song.getTitle());
        assertThat(savedSong.getArtist()).isEqualTo(song.getArtist());
        //tear down
        songRepository.delete(song);
    }

    @Test
    public void searchForSongByTitle_returnsSongsWithMatchingNames() throws Exception {
        //arrange
        //restTemplate.postForEntity("/song", TestSongs.getSongs().get(0),String.class);

        //act
        ResponseEntity<Song[]> response = restTemplate.getForEntity("/songs/title/africa", Song[].class);

        //assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        for (Song song : response.getBody()) {
            assertThat(song.getTitle()).contains("Africa");
        }
    }

    @Test
    public void searchForSongByArtist_returnsSongsWithMatchingArtist() throws Exception {
        //arrange
       // restTemplate.postForEntity("/song", TestSongs.getSongs().get(0),String.class);

        //act
        ResponseEntity<Song[]> response = restTemplate.getForEntity("/songs/artist/The Chainsmokers", Song[].class);

        //assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        for (Song song : response.getBody()) {
            assertThat(song.getArtist()).contains("The Chainsmokers");
        }
    }
}





