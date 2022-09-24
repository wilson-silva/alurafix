package br.com.alura.videoflix.controller;

import br.com.alura.videoflix.entity.Video;
import br.com.alura.videoflix.repository.VideoRepository;
import br.com.alura.videoflix.service.VideoService;
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
    public ResponseEntity<List<Video>> listAllVideos(){
        return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
    }

    //------------------------------------------------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<Object> searchVideo(@PathVariable Long id) {
        Optional<Video> videoOptional = service.searchById(id);
        if (videoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video não encontrado!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(videoOptional.get());
    }

    //------------------------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<Video> toSaveVideo(@RequestBody @Valid Video video){
        Video savedVideo = service.toSave(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVideo);
    }

    //------------------------------------------------------------------------------------------

    @PutMapping("/{id}")
    public ResponseEntity<Object> toUpdateVideo(@PathVariable Long id, @RequestBody @Valid Video video){

        Optional<Video> videoOptional = service.searchById(id);
        if(videoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video not found.");
        }
        video.setId(videoOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(service.toSave(video));

    }

    //------------------------------------------------------------------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
