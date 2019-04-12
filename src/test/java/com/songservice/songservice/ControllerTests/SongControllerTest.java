package com.songservice.songservice.ControllerTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.songservice.songservice.TestUtils.TestSongs;
import com.songservice.songservice.controllers.SongController;
import com.songservice.songservice.models.Song;
import com.songservice.songservice.repositories.SongRepository;
import com.songservice.songservice.services.SongService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SongController.class)
public class SongControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    public void postingSong_savesTheSong() throws Exception{
        Song song = TestSongs.getSongs().get(0);
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/song")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(song));
        mockMvc.perform(builder).andExpect(status().isOk());
        verify(songService, times(1)).saveSong(any(Song.class));
    }

   @Test
    public void findSongByTitle_returnsSongsWithThatTitle() throws Exception {
        //arrange
        List<Song> songs = new ArrayList<>();
        songs.add(TestSongs.getSongs().get(0));
        songs.add(TestSongs.getSongs().get(1));

        when(songService.findSongByTitle("Africa")).thenReturn(songs);

        mockMvc.perform(MockMvcRequestBuilders.get("/songs/title/Africa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Africa")))
                .andExpect(jsonPath("$[1].title", is("Africa")));

        verify(songService, times(1)).findSongByTitle("Africa");
        verifyNoMoreInteractions(songService);
    }

    @Test
    public void findSongByArtist_returnsSongsWithThatArtist() throws Exception {
        //arrange
        List<Song> songs = new ArrayList<>();
        songs.add(TestSongs.getSongs().get(5));
        songs.add(TestSongs.getSongs().get(6));

        when(songService.findSongsByArtist("The Chainsmokers")).thenReturn(songs);

        mockMvc.perform(MockMvcRequestBuilders.get("/songs/artist/The Chainsmokers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].artist", is("The Chainsmokers")))
                .andExpect(jsonPath("$[1].artist", is("The Chainsmokers")));

        verify(songService, times(1)).findSongsByArtist("The Chainsmokers");
        verifyNoMoreInteractions(songService);
    }


}
