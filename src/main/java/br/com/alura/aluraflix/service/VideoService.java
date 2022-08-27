package br.com.alura.aluraflix.service;

import br.com.alura.aluraflix.entity.Video;
import br.com.alura.aluraflix.repository.VideoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    private final VideoRepository repository;

    public VideoService(VideoRepository repository) {
        this.repository = repository;
    }

    //---------------------------------------------------------

    public List<Video> findAll() {
        return repository.findAll();
    }
    //---------------------------------------------------------

    public Optional<Video> findById(Long id) {
        return repository.findById(id);
    }
    //---------------------------------------------------------

}
