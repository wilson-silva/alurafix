package br.com.alura.videoflix.integration;

import br.com.alura.videoflix.api.request.VideoRequest;
import br.com.alura.videoflix.api.response.VideoResponse;
import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.repository.CategoryRepository;
import br.com.alura.videoflix.domain.repository.VideoRepository;
import br.com.alura.videoflix.exception.BusinessException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
        var category = categoryRepository.save(CategoryCreator.createCategory1());
        var video = videoRepository.save(VideoCreator.createVideo());

        String expectedTitle = video.getTitle();

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

    @DisplayName("should return success when searching by list of videos by category")
    @Test
    void shouldReturnSuccessWhenSearchingByListOfVideosByCategory() {

        var category = categoryRepository.save(CategoryCreator.createCategory1());
        var video = videoRepository.save(VideoCreator.createVideo());

        Long expectedId = category.getId();

        List<Video> videos = testRestTemplate
                .exchange("/videos/" + category.getId() + "/categories", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<Video>>() {
                        }).getBody();
        System.out.println(videos);
        assertThat(videos)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        assertThat(videos.get(0).getCategory().getId()).isEqualTo(expectedId);
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("should return an exception when category is not found")
    @Test
    void shouldReturnAnExceptionWhenVideoIsNotFound() throws Exception {

        ResponseEntity<BusinessException> exchange = testRestTemplate
                .exchange("/videos/" + 10L + "/categories", HttpMethod.GET, null,
                        BusinessException.class);
        assertEquals(exchange.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("should return success when the video is found by id")
    @Test
    void shouldReturnSuccessWhenTheVideoIsFoundById() {

        var category = categoryRepository.save(CategoryCreator.createCategory1());
        var video = videoRepository.save(VideoCreator.createVideo());

        Long expectedId = video.getIdentify();
        ResponseEntity<VideoResponse> response = testRestTemplate
                .exchange("/videos/" + 1L, HttpMethod.GET,
                        null, VideoResponse.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getIdentify(), expectedId);
        assertThat(response.getBody()).isNotNull();
    }

    //------------------------------------------------------------------------------------------
    @DisplayName("tests when the video is not found")
    @Test
    void searchVideoWhenTheVideoIsNotFound() {

        ResponseEntity<VideoResponse> response = testRestTemplate
                .exchange("/videos/" + 4L, HttpMethod.GET,
                        null, VideoResponse.class);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests video search when the title is not passed.")
    @Test
    void getVideoByTitleWhenTitleIsNotPassed() {

        ResponseEntity<VideoResponse> response = testRestTemplate.exchange("/videos/title?title=teste",
                HttpMethod.GET, null, VideoResponse.class);

        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isNotNull();
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests video search by title")
    @Test
    void getVideoByTitle() {

        var category = categoryRepository.save(CategoryCreator.createCategory1());
        var video = videoRepository.save(VideoCreator.createVideo());

        String expectedTitle = video.getTitle();
        String url = String.format("/videos/title?title=%s", expectedTitle);
        ResponseEntity<VideoResponse> response = testRestTemplate.exchange(url, HttpMethod.GET,
                null, VideoResponse.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getIdentify(), 1L);
        assertEquals(response.getBody().getTitle(), expectedTitle);
        assertThat(response.getBody()).isNotNull();
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to save video")
    @Test
    void saveVideo() {

        var category = categoryRepository.save(CategoryCreator.createCategory1());
        var videoRequest = VideoCreator.createVideoRequest();
        ResponseEntity<Video> videoResponse = testRestTemplate.postForEntity("/videos", videoRequest,
                Video.class);

        assertThat(videoResponse).isNotNull();
        assertEquals(videoResponse.getStatusCode(), HttpStatus.CREATED);
        assertThat(videoResponse.getBody().getIdentify()).isNotNull();
    }
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
