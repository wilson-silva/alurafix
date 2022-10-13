package br.com.alura.videoflix.domain.service;

import br.com.alura.videoflix.domain.entity.Categoria;
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

    public List<Categoria> listAll() {
      return repository.findAll();
    }
    //------------------------------------------------------------------------------------------

    public Optional<Categoria> searchById(Long id){
        return repository.findById(id);
    }
    //------------------------------------------------------------------------------------------

    @Transactional
    public Categoria toSave(Categoria categoria) {

        boolean existeCor = false;

        Optional<Categoria> categoriaOptional = repository.findBycor(categoria.getCor().getDescricao());

        if(categoriaOptional.isPresent()){
            if(!categoriaOptional.get().getId().equals(categoria.getId())){
                existeCor = true;
            }
        }

        if(existeCor){
            throw new BusinessException("cor already registered");
        }

        return repository.save(categoria);
    }
    //------------------------------------------------------------------------------------------




}
