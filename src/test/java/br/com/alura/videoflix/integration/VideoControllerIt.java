package br.com.alura.videoflix.integration;

import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.repository.CategoryRepository;
import br.com.alura.videoflix.domain.repository.VideoRepository;
import br.com.alura.videoflix.util.CategoryCreator;
import br.com.alura.videoflix.util.VideoCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class VideoControllerIt {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @LocalServerPort
    private int port;

    @DisplayName("should return success when searching for list of videos")
    @Test
    void shouldReturnSuccessWhenSearchingForListOfVideos() {
        var savedCategory = categoryRepository.save(CategoryCreator.createCategory1());
        var savedVideo = videoRepository.save(VideoCreator.createVideo());

        String expectedTitle = savedVideo.getTitle();

        List<Video> videos = testRestTemplate.exchange("/videos", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Video>>() {
                }).getBody();

        assertThat(videos)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        assertThat(videos.get(0).getTitle()).isEqualTo(expectedTitle);
    }
    //------------------------------------------------------------------------------------------

//    @DisplayName("should return success when searching by list of videos by category")
//    @Test
//    void shouldReturnSuccessWhenSearchingByListOfVideosByCategory() {
//
//        Long expectedId = 1L;
//        List<VideoResponse> list = videoController.listVideoByCategory(1L).getBody();
//
//        assertThat(list)
//                .isNotNull()
//                .isNotEmpty()
//                .hasSize(1);
//        assertThat(list.get(0).getIdentify()).isEqualTo(expectedId);
//    }
//    //------------------------------------------------------------------------------------------
//    @DisplayName("should return an empty list when category is not found")
//    @Test
//    void shouldReturnAnEmptyListWhenVideoIsNotFound() {
//
//        when(videoService.listAllVideosByCategory(anyLong())).thenReturn(Collections.emptyList());
//        when(mapper.toVideoResponseList(anyList())).thenReturn(Collections.emptyList());
//
//        List<VideoResponse> list = videoController.listVideoByCategory(2L).getBody();
//
//        assertThat(list)
//                .isNotNull()
//                .isEmpty();
//    }
//
//    //------------------------------------------------------------------------------------------
//
//    @DisplayName("should return success when the video is found by id")
//    @Test
//    void shouldReturnSuccessWhenTheVideoIsFoundById() {
//        Long expectedId = VideoCreator.createVideo().getIdentify();
//        VideoResponse videoResponse = videoController.getOneVideo(1L).getBody();
//
//        assertThat(videoResponse).isNotNull();
//
//        assertThat(videoResponse.getIdentify()).isNotNull().isEqualTo(expectedId);
//    }
//
//    //------------------------------------------------------------------------------------------
//    @DisplayName("tests when the video is not found")
//    @Test
//    void searchVideoWhenTheVideoIsNotFound() {
//
//        when(videoService.searchById(anyLong())).thenReturn(Optional.empty());
//        when(mapper.toVideoResponse(any())).thenReturn(null);
//
//        var videoResponse = videoController.getOneVideo(1L).getBody();
//        var videoResponseStatus = videoController.getOneVideo(1L);
//
//        assertThat(videoResponse)
//                .isNull();
//        assertThat(videoResponseStatus.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//
//    }
//    //------------------------------------------------------------------------------------------
//
//    @DisplayName("tests video search when the title is not passed.")
//    @Test
//    void getVideoByTitleWhenTitleIsNotPassed() {
//
//        when(videoService.searchByTitle(anyString())).thenReturn(null);
//        when(mapper.toVideoResponse(any())).thenReturn(null);
//
//        var videoResponse = videoController.getVideoByTitle("video1").getBody();
//
//        assertThat(videoResponse)
//                .isNull();
//
//    }
//    //------------------------------------------------------------------------------------------
//
//    @DisplayName("tests video search by title")
//    @Test
//    void getVideoByTitle(){
//
//        String expectedTitle = "video1";
//        var videoResponse = videoController.getVideoByTitle("video1").getBody();
//
//        assertThat(videoResponse)
//                .isNotNull();
//
//        assertThat(videoResponse.getTitle()).isEqualTo(expectedTitle);
//    }
//    //------------------------------------------------------------------------------------------
//
//    @DisplayName("tests controller to save video")
//    @Test
//    void saveVideo()  {
//        var videoResponse = videoController.saveVideo(VideoCreator.createVideoRequest()).getBody();
//        assertThat(videoResponse)
//                .isNotNull();
//        assertThat(VideoCreator.createVideo().getTitle()).isEqualTo(videoResponse.getTitle());
//    }
//    //------------------------------------------------------------------------------------------
//
//    @DisplayName("tests controller to update video")
//    @Test
//    void updateVideo() {
//
//        var videoResponse = videoController.updateVideo(1L, VideoCreator
//                .createVideoRequest()).getBody();
//
//        assertThat(videoResponse)
//                .isNotNull();
//        assertThat(VideoCreator.createVideo().getIdentify()).isEqualTo(videoResponse.getIdentify());
//    }
//    //------------------------------------------------------------------------------------------
//
//    @DisplayName("tests controller to delete video")
//    @Test
//    void deleteVideo()  {
//
//        assertThatCode(() -> videoController.deleteVideo(1L)).doesNotThrowAnyException();
//
//        ResponseEntity<Void> entity = videoController.deleteVideo(1L);
//
//        assertThat(entity).isNotNull();
//        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//
//    }


}
