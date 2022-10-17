package br.com.alura.videoflix.domain.service;

import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.repository.CategoryRepository;
import br.com.alura.videoflix.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }
    //------------------------------------------------------------------------------------------

    public List<Category> listAll() {
      return repository.findAll();
    }
    //------------------------------------------------------------------------------------------

    public Optional<Category> searchById(Long id){
        return repository.findById(id);
    }
    //------------------------------------------------------------------------------------------

    @Transactional
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

    public void deleteCategory(Long id){
        repository.deleteById(id);
    }

}
