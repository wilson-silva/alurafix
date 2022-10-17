package br.com.alura.videoflix.api.controller;

import br.com.alura.videoflix.api.mapper.VideoMapper;
import br.com.alura.videoflix.api.request.VideoRequest;
import br.com.alura.videoflix.api.response.VideoResponse;
import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.repository.VideoRepository;
import br.com.alura.videoflix.domain.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VideoController {

    private final VideoService service;

    private final VideoRepository repository;
    //------------------------------------------------------------------------------------------

    @GetMapping
    public ResponseEntity<List<VideoResponse>> listAllVideos(){
        List<Video> videos = service.listAll();
        List<VideoResponse> videoResponses = VideoMapper.toVideoResponseList(videos);
        return ResponseEntity.status(HttpStatus.OK).body(videoResponses);
    }
    //------------------------------------------------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> getOneVideo(@PathVariable Long id) {
        Optional<Video> videoOptional = service.searchById(id);
        if (videoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(VideoMapper.toVideoResponse(videoOptional.get()));
    }
    //------------------------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<VideoResponse> saveVideo(@RequestBody @Valid VideoRequest request) {
        var video = VideoMapper.toVideo(request);
        var savedVideo = service.toSave(video);
        var response = VideoMapper.toVideoResponse(savedVideo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //------------------------------------------------------------------------------------------

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponse> updateVideo(@PathVariable Long id, @RequestBody @Valid VideoRequest request){
        var video = VideoMapper.toVideo(request);
        Optional<Video> videoOptional = service.searchById(id);
        if(videoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        video.setId(videoOptional.get().getId());
        var savedVideo = service.toSave(video);
        var videoResponse = VideoMapper.toVideoResponse(savedVideo);
        return ResponseEntity.status(HttpStatus.OK).body(videoResponse);
    }
    //------------------------------------------------------------------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id){
        service.deleteVideo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
