package br.com.alura.videoflix.controller;

import br.com.alura.videoflix.entity.Video;
import br.com.alura.videoflix.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<Video>> buscarTodosOsVideos(){
        return ResponseEntity.status(HttpStatus.OK).body(service.listarTodos());
    }
    //---------------------------------------------------------

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> buscarVideo(@PathVariable Long id) {
        Optional<Video> videoOptional = service.buscarPorId(id);
        if (!videoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("video nao encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(videoOptional.get());
    }
    //---------------------------------------------------------

    @PostMapping
    public ResponseEntity<Video> salvar(@RequestBody @Valid Video video){
        Video videoSalvo = service.salvar(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(videoSalvo);
    }

}
