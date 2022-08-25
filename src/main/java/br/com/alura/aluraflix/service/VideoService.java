package br.com.alura.aluraflix.service;

import br.com.alura.aluraflix.entity.Video;
import br.com.alura.aluraflix.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository repository;

    public VideoService(VideoRepository repository) {
        this.repository = repository;
    }

    //---------------------------------------------------------
    public List<Video> findAll(){
        return repository.findAll();
    }

}
