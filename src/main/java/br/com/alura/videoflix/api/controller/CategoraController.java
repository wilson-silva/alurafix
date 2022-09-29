package br.com.alura.videoflix.api.controller;

import br.com.alura.videoflix.domain.entity.Categoria;
import br.com.alura.videoflix.domain.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @PostMapping
    public ResponseEntity<Categoria> toSaveCategory(@RequestBody @Valid Categoria categoria){
        Categoria categoriaSalva = service.toSave(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }
    //------------------------------------------------------------------------------------------



}
