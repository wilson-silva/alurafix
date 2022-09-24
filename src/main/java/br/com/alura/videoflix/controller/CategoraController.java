package br.com.alura.videoflix.controller;

import br.com.alura.videoflix.entity.Categoria;
import br.com.alura.videoflix.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoraController {

    private final CategoriaService service;
    //------------------------------------------------------------------------------------------

    @GetMapping
    public ResponseEntity<List<Categoria>> listAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(service.listAll());
    }
    //------------------------------------------------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<Object> searchCategory(@PathVariable Long id){
        Optional<Categoria> categoriaOptional = service.searchById(id);
        if(categoriaOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoriaOptional.get());
    }
    //------------------------------------------------------------------------------------------



}
