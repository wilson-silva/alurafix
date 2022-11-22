package br.com.alura.videoflix.api.controller;

import br.com.alura.videoflix.api.mapper.VideoMapper;
import br.com.alura.videoflix.api.response.VideoResponse;
import br.com.alura.videoflix.domain.service.VideoService;
import br.com.alura.videoflix.util.VideoAndCategoryCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class VideoControllerTest {

    @InjectMocks
    private VideoController videoController;
    @Mock
    private VideoService videoService;
    @Mock
    private VideoMapper mapper;
    //------------------------------------------------------------------------------------------

    @BeforeEach
    public void setup() throws JsonProcessingException {

        var listVideo = List.of(VideoAndCategoryCreator.createVideo());
        List<VideoResponse> listResponse = List.of(VideoAndCategoryCreator.createVideoResponse());

        when(videoService.listAllVideos()).thenReturn(listVideo);
        when(videoService.listAllVideosByCategory(anyLong())).thenReturn(listVideo);
        when(mapper.toVideoResponseList(anyList())).thenReturn(listResponse);
        when(videoService.searchById(anyLong())).thenReturn(Optional.of(VideoAndCategoryCreator.createVideo()));

        when(videoService.searchByTitle(anyString())).thenReturn(VideoAndCategoryCreator.createVideo());

        //usado no método salvar
        when(mapper.toVideo(any())).thenReturn(VideoAndCategoryCreator.createVideo());
        when(videoService.toSave(any())).thenReturn(VideoAndCategoryCreator.createVideo());

        when(mapper.toVideoResponse(any())).thenReturn(VideoAndCategoryCreator.createVideoResponse());

    }
    //------------------------------------------------------------------------------------------

    @DisplayName("should return success when searching for list of videos")
    @Test
    void shouldReturnSuccessWhenSearchingForListOfVideos() {

        String expectedTitle = "video1";
        List<VideoResponse> list = videoController.listAllVideos().getBody();

        assertThat(list).isNotNull();
        assertThat(list)
                .isNotEmpty()
                .hasSize(1);
        assertThat(list.get(0).getTitle()).isEqualTo(expectedTitle);
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("should return success when searching by list of videos by category")
    @Test
    void shouldReturnSuccessWhenSearchingByListOfVideosByCategory() {

        Long expectedId = 1L;
        List<VideoResponse> list = videoController.listVideoByCategory(1L).getBody();

        assertThat(list).isNotNull();
        assertThat(list)
                .isNotEmpty()
                .hasSize(1);
        assertThat(list.get(0).getIdentify()).isEqualTo(expectedId);
    }

    //------------------------------------------------------------------------------------------
    @DisplayName("should return an empty list when video is not found")
    @Test
    void shouldReturnAnEmptyListWhenVideoIsNotFound() {

        when(videoService.listAllVideosByCategory(anyLong())).thenReturn(Collections.emptyList());
        when(mapper.toVideoResponseList(anyList())).thenReturn(Collections.emptyList());

        List<VideoResponse> list = videoController.listVideoByCategory(2L).getBody();

        assertThat(list)
                .isNotNull()
                .isEmpty();
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("should return success when the video is found by id")
    @Test
    void shouldReturnSuccessWhenTheVideoIsFoundById() {
        Long expectedId = 1L;
        VideoResponse videoResponse = videoController.getOneVideo(1L).getBody();

        assertThat(videoResponse).isNotNull();

        assertThat(videoResponse.getIdentify()).isEqualTo(expectedId);
    }

    //------------------------------------------------------------------------------------------
    @DisplayName("tests when the video is not found")
    @Test
    void searchVideoWhenTheVideoIsNotFound() {

        when(videoService.searchById(anyLong())).thenReturn(Optional.empty());
        when(mapper.toVideoResponse(any())).thenReturn(null);

        var videoResponse = videoController.getOneVideo(1L).getBody();

        assertThat(videoResponse)
                .isNull();

    }
   //------------------------------------------------------------------------------------------

    @DisplayName("tests video search when the title is not passed.")
    @Test
    void getVideoByTitleWhenTitleIsNotPassed() {

        when(videoService.searchByTitle(anyString())).thenReturn(null);
        when(mapper.toVideoResponse(any())).thenReturn(null);

        var videoResponse = videoController.getVideoByTitle("video1").getBody();

        assertThat(videoResponse)
                .isNull();

    }
   //------------------------------------------------------------------------------------------

    @DisplayName("tests video search by title")
    @Test
    void getVideoByTitle(){

        String expectedTitle = "video1";
        var videoResponse = videoController.getVideoByTitle("video1").getBody();

        assertThat(videoResponse)
                .isNotNull();

        assertThat(videoResponse.getTitle()).isEqualTo(expectedTitle);
    }
   //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to save video")
    @Test
    void saveVideo()  {
        var videoResponse = videoController.saveVideo(VideoAndCategoryCreator.createVideoToBeSave()).getBody();
        assertThat(videoResponse)
                .isNotNull();
        assertThat(VideoAndCategoryCreator.createVideoToBeSave().getTitle()).isEqualTo(videoResponse.getTitle());
    }
//    //------------------------------------------------------------------------------------------
//
//    @DisplayName("tests controller to update video")
//    @Test
//    void updateVideo() throws Exception {
//
//        Long ID = 1L;
//        String URL = "/videos/{id}";
//        var category = Category.builder().id(1L).title("title1").color("color1").build();
//        var video1 = Video.builder().identify(1L).title("video1").description("video1")
//                .url("video1@video1.com").category(category).build();
//
//        when(videoService.updateVideo(ID, video1)).thenReturn(video1);
//
//        mockMvc.perform(put(URL, ID)
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(video1)))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//    //------------------------------------------------------------------------------------------
//
//    @DisplayName("tests controller to delete video")
//    @Test
//    void deleteVideo() throws Exception {
//        Long ID = 1L;
//        String URL = "/videos/{id}";
//        var category = Category.builder().id(1L).title("title1").color("color1").build();
//        var video1 = Video.builder().identify(1L).title("video1").description("video1")
//                .url("video1@video1.com").category(category).build();
//        when(videoService.searchById(video1.getIdentify())).thenReturn(Optional.of(video1));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .delete(URL, ID)
//                .contentType("application/json"))
//                .andExpect(status().isNoContent());
//    }
}