package br.com.alura.videoflix.domain.service;

import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.repository.VideoRepository;
import br.com.alura.videoflix.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@WebAppConfiguration
class VideoServiceTest {

    @MockBean
    private VideoRepository repository;
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private VideoService videoService;

    @Captor
    ArgumentCaptor<Video> videoCaptor;

    private Video video;
    private Video video2;
    private Category category;
    private Category category2;

    @BeforeEach
    public void setUp() {
        category = Category.builder()
                .id(1L)
                .title("free")
                .build();

        category2 = Category.builder()
                .id(2L)
                .title("back-end")
                .build();

        video = Video.builder()
                .identify(1L)
                .title("teste")
                .description("teste")
                .url("teste@teste.com")
                .category(category)
                .build();

        video2 = Video.builder()
                .identify(2L)
                .title("teste2")
                .description("teste2")
                .url("teste2@teste.com")
                .category(category2)
                .build();
    }
    //------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Should save videos successfully")
    void saveSuccessfully() {

        when(repository.findByUrl(video.getUrl()))
                .thenReturn(Optional.empty());

        when(categoryService.searchById(video.getCategory().getId()))
                .thenReturn(Optional.of(category));

        videoService.toSave(video);

        verify(repository).findByUrl(video.getUrl());
        verify(categoryService).searchById(video.getCategory().getId());

        verify(repository).save(videoCaptor.capture());
        Video videoSaved = videoCaptor.getValue();
        assertThat(videoSaved.getCategory()).isNotNull();

        assertDoesNotThrow(() -> videoService.toSave(video));
    }
    //------------------------------------------------------------------------------------------

    @Test
    @DisplayName("Should not save videos successfully")
    void saveNotSuccessfully() {

        when(repository.findByUrl(video.getUrl()))
                .thenReturn(Optional.of(video2));

        when(categoryService.searchById(video.getCategory().getId()))
                .thenReturn(Optional.of(category));

        var ex = assertThrows(BusinessException.class, () -> videoService.toSave(video),
                "saved video");
        assertEquals("video already registered", ex.getMessage());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should get all videos")
    @Test
    void getAllVideos() {

        List<Video> videos = List.of(video, video2);

        when(repository.findAll())
                .thenReturn(videos);

        videoService.listAllVideos();

        verify(repository).findAll();
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should get one video")
    @Test
    void shouldGetOneVideo() {
        Long id = 1L;

        when(repository.findById(id))
                .thenReturn(Optional.ofNullable(video));

        videoService.searchById(id);

        verify(repository).findById(1L);
        assertEquals(video.getIdentify(), 1L);
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should return exception when category not found")
    @Test
    void shouldReturnExceptionWhenCategoryNotFound() {

        when(repository.findById(video.getIdentify()))
                .thenReturn(Optional.ofNullable(video));

        when(categoryService.searchById(2L))
                .thenReturn(Optional.empty());

        var ex = assertThrows(BusinessException.class, () -> videoService.toSave(video),
                "saved video");
        assertEquals("categoty not found!", ex.getMessage());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should return exception when the video is not found in the update")
    @Test
    void shouldReturnExceptionWhenVideoIsNotFoundInTheUpdate() {
        Long id = 2L;

        when(videoService.searchById(id))
                .thenReturn(Optional.empty());

        when(categoryService.searchById(video.getCategory().getId()))
                .thenReturn(Optional.of(category));

        var ex = assertThrows(BusinessException.class, () ->
                videoService.updateVideo(id, video), "video found");
        assertEquals("Video not found!", ex.getMessage());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("test when change method is called")
    @Test
    void testWhenChangeMethodIsCalled() {

        Long id = 1L;

        when(videoService.searchById(id))
                .thenReturn(Optional.ofNullable(video));

        when(categoryService.searchById(video.getCategory().getId()))
                .thenReturn(Optional.of(category));

        videoService.updateVideo(id, video);

        verify(repository).save(videoCaptor.capture());
        Video videoSaved = videoCaptor.getValue();
        assertThat(videoSaved.getCategory()).isNotNull();
        assertDoesNotThrow(() -> videoService.updateVideo(id, video));
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should delete a video")
    @Test
    void shouldDeleteAVideo() {
        Long id = 1L;

        when(videoService.searchById(id)).thenReturn(Optional.of(video));

        videoService.deleteVideo(id);
        verify(repository).delete(video);

        assertDoesNotThrow(() -> videoService.deleteVideo(id));
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should return exception when the video is not found in the delete")
    @Test
    void shouldReturnExceptionWhenTheVideoIsNotFoundInTheDelete() {

        when(videoService.searchById(video.getIdentify()))
                .thenReturn(Optional.empty());

        var ex = assertThrows(BusinessException.class, () ->
                videoService.deleteVideo(2L), "video found");
        assertEquals("Video not found!", ex.getMessage());
    }

    //------------------------------------------------------------------------------------------
    @DisplayName("Test method listAllVideoCategory")
    @Test
    void TestMethodListAllVideoCategory() {

        List<Video> videos = List.of(video, video2);

        when(repository.findByCategoryId(1L))
                .thenReturn(videos);

        videoService.listAllVideosByCategory(category.getId());

        verify(repository).findByCategoryId(category.getId());

        assertDoesNotThrow(() -> videoService.listAllVideosByCategory(category.getId()));
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should return exception when there is no video for the given category")
    @Test
    void shouldReturnExceptionWhenThereIsNoVideoForTheGivenCategory() {

        List<Video> videos1 = List.of(video);
        List<Video> videos2 = List.of(video2);

        when(repository.findByCategoryId(video.getCategory().getId()))
                .thenReturn(videos1);

        var ex = assertThrows(BusinessException.class, () ->
                videoService.listAllVideosByCategory(video2.getCategory().getId()), "video found for category");
        assertEquals("There is no video for the given category!", ex.getMessage());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should return exception when there is no title in the video")
    @Test
    void shouldReturnExceptionWhenThereIsNoTitleInTheVideo() {

        when(repository.findByTitle(video.getTitle()))
                .thenReturn(Optional.of(video));

        var ex = assertThrows(BusinessException.class, () ->
                videoService.searchByTitle(video2.getTitle()), "video found for title");
        assertEquals("video not found!", ex.getMessage());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Test method searchByTitle")
    @Test
    void testNethodSearchByTitle() {

        when(repository.findByTitle("teste"))
                .thenReturn(Optional.of(video));

        videoService.searchByTitle("teste");

        verify(repository).findByTitle(video.getTitle());

        assertDoesNotThrow(() -> videoService.searchByTitle(video.getTitle()));
    }

}