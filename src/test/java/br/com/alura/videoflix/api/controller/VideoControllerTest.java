package br.com.alura.videoflix.api.controller;

import br.com.alura.videoflix.api.mapper.VideoMapper;
import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.repository.VideoRepository;
import br.com.alura.videoflix.domain.service.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VideoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private VideoService videoService;

    @MockBean
    private VideoMapper videoMapper;

    @MockBean
    private VideoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;
    //------------------------------------------------------------------------------------------

    @DisplayName("tests the video list")
    @Test
    void listAllVideos() throws Exception {
        List<Video> listVideo = new ArrayList<>();
        var category = Category.builder().id(1L).title("title1").color("color1").build();
        listVideo.add(Video.builder().identify(1L).title("video1").description("description1")
                .url("video1@video1.com").category(category).build());
        listVideo.add(Video.builder().identify(2L).title("video2").description("description2")
                .url("video2@video2.com").category(category).build());

        when(videoService.listAllVideos()).thenReturn(listVideo);

        mockMvc.perform(get("/videos")).andExpect(status().isOk());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests the video list search by category")
    @Test
    void listAllVideosByCategories() throws Exception {
        Long ID = 1L;
        String URL = "/videos/{id}/categories";
        var category = Category.builder().id(1L).title("title1").color("color1").build();
        List<Video> videoList = new ArrayList<>();
        videoList.add(Video.builder().identify(1L).title("video1").description("video1")
                .url("video1@video1.com").category(category).build());

        when(videoService.listAllVideosByCategory(1L)).thenReturn(videoList);

        mockMvc.perform(get(URL, ID)).andExpect(status().isOk());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests video search by id")
    @Test
    void searchCategory() throws Exception {
        Long ID = 1L;
        String URL = "/videos/{id}";
        var category = Category.builder().id(1L).title("title1").color("color1").build();
        var video1 = Video.builder().identify(1L).title("video1").description("video1")
                .url("video1@video1.com").category(category).build();

        when(videoService.searchById(ID)).thenReturn(Optional.of(video1));

        mockMvc.perform(get(URL, ID)).andExpect(status().isOk());
    }

    //------------------------------------------------------------------------------------------

    @DisplayName("tests when the video is not found")
    @Test
    void searchCategoryWhenTheCategoryIsNotFound() throws Exception {
        Long ID = 1L;
        String URL = "/videos/{id}";

        when(videoService.searchById(ID)).thenReturn(Optional.empty());

        mockMvc.perform(get(URL, ID)).andExpect(status().isNotFound());
    }
     //------------------------------------------------------------------------------------------

    @DisplayName("tests video search when the title is not passed.")
    @Test
    void getVideoByTitleWhenTitleIsNotPassed() throws Exception {
        var category = Category.builder().id(1L).title("title1").color("color1").build();
        var video1 = Video.builder().identify(1L).title("video1").description("video1")
                .url("video1@video1.com").category(category).build();

        when(videoService.searchByTitle("teste")).thenReturn(video1);

        mockMvc.perform(get("/videos/title"))
                .andExpect(status().isBadRequest());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests video search by title")
    @Test

    void getVideoByTitle() throws Exception {
        String title = "video1";
        var category = Category.builder().id(1L).title("title1").color("color1").build();
        var video1 = Video.builder().identify(1L).title("video1").description("video1")
                .url("video1@video1.com").category(category).build();

        when(videoService.searchByTitle(title)).thenReturn(video1);
        mockMvc.perform(get("/videos/title").param("title", title))
                .andExpect(status().isOk());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to save video")
    @Test
    void saveCategory() throws Exception {

        var category = Category.builder().id(1L).title("title1").color("color1").build();
        var video1 = Video.builder().identify(1L).title("video1").description("video1")
                .url("video1@video1.com").category(category).build();

        when(videoService.toSave(video1)).thenReturn(video1);

        mockMvc.perform(post("/videos")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(video1)))
                .andExpect(status().isCreated());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to change video")
    @Test
    void updateCategory() throws Exception {

        Long ID = 1L;
        String URL = "/videos/{id}";
        var category = Category.builder().id(1L).title("title1").color("color1").build();
        var video1 = Video.builder().identify(1L).title("video1").description("video1")
                .url("video1@video1.com").category(category).build();

        when(videoService.updateVideo(ID, video1)).thenReturn(video1);

        mockMvc.perform(put(URL, ID)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(video1)))
                .andExpect(status().isOk());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to delete video")
    @Test
    void deleteCategory() throws Exception {
        Long ID = 1L;
        String URL = "/videos/{id}";
        var category = Category.builder().id(1L).title("title1").color("color1").build();
        var video1 = Video.builder().identify(1L).title("video1").description("video1")
                .url("video1@video1.com").category(category).build();
        when(videoService.searchById(video1.getIdentify())).thenReturn(Optional.of(video1));

        mockMvc.perform(MockMvcRequestBuilders
                .delete(URL, ID)
                .contentType("application/json"))
                .andExpect(status().isNoContent());
    }
}