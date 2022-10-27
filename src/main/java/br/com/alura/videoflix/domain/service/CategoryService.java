package br.com.alura.videoflix.domain.service;

import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.repository.CategoryRepository;
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
public class CategoryService {

    private final CategoryRepository repository;

    //------------------------------------------------------------------------------------------

    @Cacheable(value = "listCategory")
    public List<Category> listAll() {
      return repository.findAll();
    }
    //------------------------------------------------------------------------------------------

    public Optional<Category> searchById(Long id){
        return repository.findById(id);
    }
    //------------------------------------------------------------------------------------------
    @CacheEvict(value = "listCategory", allEntries = true)
    public Category toSave(Category category) {

        boolean thereIsCategory = false;

        Optional<Category> categoryOptional = repository.findByTitleOrColor(category.getTitle(),
                category.getColor());

        if(categoryOptional.isPresent()){
            if(!(categoryOptional.get().getId().equals(category.getId()))){
                thereIsCategory = true;
            }
        }

        if(thereIsCategory){
            throw new BusinessException("category already registered");
        }

        return repository.save(category);
    }
    //------------------------------------------------------------------------------------------
    @CacheEvict(value = "listCategory", allEntries = true)
    public Category updateCategory(Long id, Category category) {
        Optional<Category> categoryOptional = this.searchById(id);
        if(categoryOptional.isEmpty()){
            throw new BusinessException("Category not found!");
        }
        category.setId(id);
        return toSave(category);
    }
    //------------------------------------------------------------------------------------------
    @CacheEvict(value = "listCategory", allEntries = true)
    public void deleteCategory(Long id){
        repository.deleteById(id);
    }
    //------------------------------------------------------------------------------------------
    public List<Video> listVideoByCategory(Long id) {
        Category category = repository.findById(id).orElseThrow(
                () -> new BusinessException("Category not found!"));
        List<Video> videos = category.getVideos();
        if(videos.isEmpty()) throw new BusinessException("There is no video for the given category");
        return videos;
    }

}
