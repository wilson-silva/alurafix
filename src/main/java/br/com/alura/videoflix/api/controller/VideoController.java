package br.com.alura.videoflix.api.controller;

import br.com.alura.videoflix.api.mapper.VideoMapper;
import br.com.alura.videoflix.api.request.VideoRequest;
import br.com.alura.videoflix.api.response.VideoResponse;
import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/videos")

public class VideoController {

    private final VideoService service;
    private final VideoMapper mapper;

    //------------------------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<VideoResponse>> listAllVideos() {
        List<Video> videos = service.listAllVideos();
        List<VideoResponse> videoResponses = mapper.toVideoResponseList(videos);
        return ResponseEntity.status(HttpStatus.OK).body(videoResponses);
    }

    @GetMapping("/{id}/categories")
    public ResponseEntity<List<VideoResponse>> listVideoByCategory(@PathVariable Long id) {
        List<Video> videos = service.listAllVideosByCategory(id);
        List<VideoResponse> videoResponses = mapper.toVideoResponseList(videos);
        return ResponseEntity.status(HttpStatus.OK).body(videoResponses);
    }

    //------------------------------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> getOneVideo(@PathVariable Long id) {
        Optional<Video> videoOptional = service.searchById(id);
        if (videoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toVideoResponse(videoOptional.get()));
    }

    @GetMapping(value = "/title")
    public ResponseEntity<VideoResponse> getVideoByTitle(
            @RequestParam(value = "title") String title) {
        Video video = service.searchByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toVideoResponse(video));
    }
    //------------------------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<VideoResponse> saveVideo(@RequestBody VideoRequest request) {
        var video = mapper.toVideo(request);
        var savedVideo = service.toSave(video);
        var response = mapper.toVideoResponse(savedVideo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //------------------------------------------------------------------------------------------

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponse> updateVideo(@PathVariable Long id, @RequestBody VideoRequest request) {

        var video = mapper.toVideo(request);
        var savedVideo = service.updateVideo(id, video);
        var videoResponse = mapper.toVideoResponse(savedVideo);
        return ResponseEntity.status(HttpStatus.OK).body(videoResponse);
    }

    //------------------------------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        service.deleteVideo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
