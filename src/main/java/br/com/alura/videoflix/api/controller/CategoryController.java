package br.com.alura.videoflix.api.controller;

import br.com.alura.videoflix.api.mapper.CategoryMapper;
import br.com.alura.videoflix.api.mapper.VideoMapper;
import br.com.alura.videoflix.api.request.CategoryRequest;
import br.com.alura.videoflix.api.response.CategoryResponse;
import br.com.alura.videoflix.api.response.VideoResponse;
import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;
    private final CategoryMapper mapper;

    private final VideoMapper videoMapper;
    //------------------------------------------------------------------------------------------

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> listAllCategories() {
        List<Category> categories = service.listAll();
        List<CategoryResponse> categoryResponses = mapper.toCategoryResponseList(categories);
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponses);
    }

    @GetMapping("/{id}/videos")
    public ResponseEntity<List<VideoResponse>> listAllVideosByCategories(@PathVariable Long id) {
        List<Video> videos = service.listVideoByCategory(id);
        List<VideoResponse> videoResponses = videoMapper.toVideoResponseList(videos);
        return ResponseEntity.status(HttpStatus.OK).body(videoResponses);
    }
    //------------------------------------------------------------------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> searchCategory(@PathVariable Long id) {
        Optional<Category> categoryOptional = service.searchById(id);
        if (categoryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toCategoryResponse(categoryOptional.get()));
    }

    //------------------------------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory(@Valid @RequestBody CategoryRequest request) {
        var category = mapper.toCategory(request);
        var savedCategory = service.toSave(category);
        var response = mapper.toCategoryResponse(savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    //------------------------------------------------------------------------------------------

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody
    @Valid CategoryRequest request) {
        var category = mapper.toCategory(request);
        var savedCategory = service.updateCategory(id, category);
        var categoryResponse = mapper.toCategoryResponse(savedCategory);
        return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
    }
    //------------------------------------------------------------------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
