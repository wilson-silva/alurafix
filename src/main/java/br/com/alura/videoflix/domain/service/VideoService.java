package br.com.alura.videoflix.domain.service;

import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.repository.VideoRepository;
import br.com.alura.videoflix.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    private VideoRepository repository;

    public VideoService(VideoRepository repository) {
        this.repository = repository;
    }
    //------------------------------------------------------------------------------------------

    public List<Video> listAll() {
        return repository.findAll();

    }
    //------------------------------------------------------------------------------------------

    public Optional<Video> searchById(Long id) {
        return repository.findById(id);
    }
    //------------------------------------------------------------------------------------------

    @Transactional
    public Video toSave(Video video){

        boolean thereIsUrl = false;

        Optional<Video> videoOptional = repository.findByUrl(video.getUrl());

        if(videoOptional.isPresent()){
            if(!videoOptional.get().getId().equals(video.getId())){
                thereIsUrl = true;
            }
        }

        if(thereIsUrl){
           throw new BusinessException("url already registered");
        }
        return repository.save(video);
    }
    //------------------------------------------------------------------------------------------
    public Video updateVideo(Long id, Video video) {
        Optional<Video> videoOptional = this.searchById(id);
        if(videoOptional.isEmpty()){
            throw new BusinessException("Video not found!");
        }
        video.setId(id);
        return toSave(video);
    }
    //------------------------------------------------------------------------------------------
    @Transactional
    public void deleteVideo(Long id){
        repository.deleteById(id);
    }

}
