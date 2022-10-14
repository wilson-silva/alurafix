package br.com.alura.videoflix.domain.service;

import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.repository.CategoriaRepository;
import br.com.alura.videoflix.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
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

        boolean existeCategory = false;

        Optional<Category> categoriaOptional = repository.findByTitle(category.getTitle());


        if(categoriaOptional.isPresent()){
            if(!(categoriaOptional.get().getId().equals(category.getId()))){
                existeCategory = true;
            }
        }

        if(existeCategory){
            throw new BusinessException("category already registered");
        }

        return repository.save(category);
    }
    //------------------------------------------------------------------------------------------




}
