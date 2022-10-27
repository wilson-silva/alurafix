package br.com.alura.videoflix.domain.service;

import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.repository.VideoRepository;
import br.com.alura.videoflix.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository repository;
    private final CategoryService categoryService;

    //------------------------------------------------------------------------------------------

    public List<Video> listAllVideoCategory() {
        return repository.findAll();
    }
    //------------------------------------------------------------------------------------------

    public Optional<Video> searchById(Long id) {
        return repository.findById(id);
    }
    //------------------------------------------------------------------------------------------
    @CacheEvict(value = "listCategory", allEntries = true)
    public Video toSave(Video video) {

        boolean thereIsVideo = false;

        Optional<Video> videoOptional = repository.findByUrl(video.getUrl());

        if (videoOptional.isPresent()) {
            if (!(videoOptional.get().getIdentify().equals(video.getIdentify()))) {
                thereIsVideo = true;
            }
        }

        if (thereIsVideo) {
            throw new BusinessException("video already registered");
        }

        Optional<Category> optionalCategory = categoryService.searchById(video.getCategory().getId());

        if (optionalCategory.isEmpty()) {
            throw new BusinessException("categoty not found!");
        }

        video.setCategory(optionalCategory.get());

        return repository.save(video);
    }

    //------------------------------------------------------------------------------------------
    @CacheEvict(value = "listCategory", allEntries = true)
    public Video updateVideo(Long id, Video video) {
        Optional<Video> videoOptional = this.searchById(id);
        if (videoOptional.isEmpty()) {
            throw new BusinessException("Video not found!");
        }
        video.setIdentify(id);
        return toSave(video);
    }

    //------------------------------------------------------------------------------------------
    @CacheEvict(value = "listCategory", allEntries = true)
    public void deleteVideo(Long id) {
        repository.deleteById(id);
    }
    //------------------------------------------------------------------------------------------

    public List<Video> listAllVideoCategory(Long id) {
        List<Video> videoList = repository.findByCategoryId(id);
        if (videoList.isEmpty()) throw new BusinessException("There is no video for the given category");
        return videoList;
    }

    public Video searchByTitle(String title) {
        return repository.findByTitle(title).orElseThrow(
                () -> new BusinessException("video not found!")
        );
    }
}
