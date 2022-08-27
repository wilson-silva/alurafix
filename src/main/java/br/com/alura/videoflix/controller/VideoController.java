package br.com.alura.videoflix.controller;

import br.com.alura.videoflix.entity.Video;
import br.com.alura.videoflix.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService service;

    public VideoController(VideoService service) {
        this.service = service;
    }
    //---------------------------------------------------------

    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos(){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }
    //---------------------------------------------------------

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getOneVideo(@PathVariable Long id) {
        Optional<Video> videoOptional = service.findById(id);
        if (!videoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("video nao encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(videoOptional.get());
    }
    //---------------------------------------------------------
}
