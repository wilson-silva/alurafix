package br.com.alura.videoflix.api.controller;

import br.com.alura.videoflix.api.mapper.CategoriaMapper;
import br.com.alura.videoflix.api.request.CategoriaRequest;
import br.com.alura.videoflix.api.response.CategoriaResponse;
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
public class CategoriaController {

    private final CategoriaService service;
    //------------------------------------------------------------------------------------------

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listAllCategories() {
        List<Categoria> categorias = service.listAll();
        List<CategoriaResponse> categoriaResponses = CategoriaMapper.toCategoriaResponseList(categorias);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaResponses);
    }
    //------------------------------------------------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> searchCategory(@PathVariable Long id) {
        Optional<Categoria> categoriaOptional = service.searchById(id);
        if (categoriaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(CategoriaMapper.toCategoriaResponse(categoriaOptional.get()));
    }

    //------------------------------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<CategoriaResponse> toSaveCategory(@RequestBody @Valid CategoriaRequest request) {
        var categoria = CategoriaMapper.toCategoria(request);
        var categoriaSalva = service.toSave(categoria);
        var response = CategoriaMapper.toCategoriaResponse(categoriaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //------------------------------------------------------------------------------------------

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> toUpdateCategory(@PathVariable Long id, @RequestBody
    @Valid CategoriaRequest request) {
        var categoria = CategoriaMapper.toCategoria(request);
        Optional<Categoria> categoriaOptional = service.searchById(id);
        if(categoriaOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        categoria.setId(categoriaOptional.get().getId());
        var saveCategoria = service.toSave(categoria);
        var categoriaResponse = CategoriaMapper.toCategoriaResponse(saveCategoria);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaResponse);
    }
    //------------------------------------------------------------------------------------------


}
