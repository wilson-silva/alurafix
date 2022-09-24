package br.com.alura.videoflix.service;

import br.com.alura.videoflix.entity.Categoria;
import br.com.alura.videoflix.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

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



}
