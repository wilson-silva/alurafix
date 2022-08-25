package br.com.alura.aluraflix.controller;

import br.com.alura.aluraflix.entity.Video;
import br.com.alura.aluraflix.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService service;

    public VideoController(VideoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos(){
        return ResponseEntity.ok(service.findAll());
    }
}
