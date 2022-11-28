package br.com.alura.videoflix.api.controller;

import br.com.alura.videoflix.api.mapper.VideoMapper;
import br.com.alura.videoflix.api.response.VideoResponse;
import br.com.alura.videoflix.domain.service.VideoService;
import br.com.alura.videoflix.util.ListVideoCreator;
import br.com.alura.videoflix.util.VideoCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
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

        when(videoService.searchById(anyLong())).thenReturn(Optional.of(VideoCreator.createVideo()));
        when(videoService.searchByTitle(anyString())).thenReturn(VideoCreator.createVideo());
        when(videoService.toSave(any())).thenReturn(VideoCreator.createVideo());

        when(mapper.toVideoResponseList(anyList())).thenReturn(ListVideoCreator.createListResponse());
        when(mapper.toVideo(any())).thenReturn(VideoCreator.createVideo());
        when(mapper.toVideoResponse(any())).thenReturn(VideoCreator.createVideoResponse());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("should return success when searching for list of videos")
    @Test
    void shouldReturnSuccessWhenSearchingForListOfVideos() {

        String expectedTitle = "video1";
        List<VideoResponse> videos = videoController.listAllVideos().getBody();

        assertThat(videos)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        assertThat(videos.get(0).getTitle()).isEqualTo(expectedTitle);
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("should return success when searching by list of videos by category")
    @Test
    void shouldReturnSuccessWhenSearchingByListOfVideosByCategory() {

        Long expectedId = 1L;
        List<VideoResponse> list = videoController.listVideoByCategory(1L).getBody();

        assertThat(list)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        assertThat(list.get(0).getIdentify()).isEqualTo(expectedId);
    }
    //------------------------------------------------------------------------------------------
    @DisplayName("should return an empty list when category is not found")
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
        Long expectedId = VideoCreator.createVideo().getIdentify();
        VideoResponse videoResponse = videoController.getOneVideo(1L).getBody();

        assertThat(videoResponse).isNotNull();

        assertThat(videoResponse.getIdentify()).isNotNull().isEqualTo(expectedId);
    }

    //------------------------------------------------------------------------------------------
    @DisplayName("tests when the video is not found")
    @Test
    void searchVideoWhenTheVideoIsNotFound() {

        when(videoService.searchById(anyLong())).thenReturn(Optional.empty());
        when(mapper.toVideoResponse(any())).thenReturn(null);

        var videoResponse = videoController.getOneVideo(1L).getBody();
        var videoResponseStatus = videoController.getOneVideo(1L);

        assertThat(videoResponse)
                .isNull();
        assertThat(videoResponseStatus.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

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
        var videoResponse = videoController.saveVideo(VideoCreator.createVideoRequest()).getBody();
        assertThat(videoResponse)
                .isNotNull();
        assertThat(VideoCreator.createVideo().getTitle()).isEqualTo(videoResponse.getTitle());
    }
   //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to update video")
    @Test
    void updateVideo() {

        var videoResponse = videoController.updateVideo(1L, VideoCreator
                .createVideoRequest()).getBody();

        assertThat(videoResponse)
                .isNotNull();
        assertThat(VideoCreator.createVideo().getIdentify()).isEqualTo(videoResponse.getIdentify());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to delete video")
    @Test
    void deleteVideo()  {

        assertThatCode(() -> videoController.deleteVideo(1L)).doesNotThrowAnyException();

        ResponseEntity<Void> entity = videoController.deleteVideo(1L);

        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}