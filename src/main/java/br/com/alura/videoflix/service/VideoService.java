package br.com.alura.videoflix.service;

import br.com.alura.videoflix.entity.Video;
import br.com.alura.videoflix.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    private final VideoRepository repository;

    public VideoService(VideoRepository repository) {
        this.repository = repository;
    }

    //---------------------------------------------------------

    public List<Video> listarTodos() {
        return repository.findAll();
    }
    //---------------------------------------------------------

    public Optional<Video> buscarPorId(Long id) {
        return repository.findById(id);
    }
    //---------------------------------------------------------

    public Video salvar(Video video){
        return repository.save(video);
    }

}
