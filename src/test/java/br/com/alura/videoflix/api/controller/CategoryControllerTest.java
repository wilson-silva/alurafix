package br.com.alura.videoflix.api.controller;

import br.com.alura.videoflix.api.mapper.CategoryMapper;
import br.com.alura.videoflix.api.mapper.VideoMapper;
import br.com.alura.videoflix.api.response.CategoryResponse;
import br.com.alura.videoflix.api.response.VideoResponse;
import br.com.alura.videoflix.domain.service.CategoryService;
import br.com.alura.videoflix.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService service;

    @Mock
    private CategoryMapper mapper;

    @Mock
    private VideoMapper videoMapper;
    //------------------------------------------------------------------------------------------
    @BeforeEach
    public void setup() throws JsonProcessingException {

        when(service.listAllCategories()).thenReturn(ListCategoryCreator.createListCategory());
        when(service.listVideoByCategory(anyLong())).thenReturn(ListVideoCreator.createListVideo());
        when(service.searchById(anyLong())).thenReturn(Optional.of(CategoryCreator.createCategory()));
        when(service.toSave(any())).thenReturn(CategoryCreator.createCategory());
        when(service.updateCategory(anyLong(), any())).thenReturn(CategoryCreator.createCategory());

        when(mapper.toCategoryResponseList(anyList())).thenReturn(ListCategoryCreator.createListCategoryResponse());
        when(videoMapper.toVideoResponseList(anyList())).thenReturn(ListVideoCreator.createListResponse());
        when(mapper.toCategoryResponse(any())).thenReturn(CategoryResponseCreator.createCategoryResponse());

    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests the category list")
    @Test
    void listAllCategories() {
        String expectedTitle = "title1";
        List<CategoryResponse> categoryResponseList = categoryController.listAllCategories().getBody();

        assertThat(categoryResponseList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        assertThat(categoryResponseList.get(0).getTitle()).isEqualTo(expectedTitle);
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests the video list search by category")
    @Test
    void listAllVideosByCategories() {
        Long expectedId = 1L;
        List<VideoResponse> videoResponseList = categoryController.listAllVideosByCategories(1L).getBody();

        assertThat(videoResponseList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        assertThat(videoResponseList.get(0).getIdentify()).isEqualTo(expectedId);
    }
      //------------------------------------------------------------------------------------------

    @DisplayName("tests category search by id")
    @Test
    void searchCategory()  {
        Long expectedId = CategoryCreator.createCategory().getId();
        var categoryResponse = categoryController.searchCategory(1L).getBody();

        assertThat(categoryResponse).isNotNull();

        assertThat(categoryResponse.getId()).isNotNull().isEqualTo(expectedId);
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests when the category is not found")
    @Test
    void searchCategoryWhenTheCategoryIsNotFound() throws Exception {

        when(service.searchById(anyLong())).thenReturn(Optional.empty());
        when(mapper.toCategoryResponse(any())).thenReturn(null);

        var categoryResponse = categoryController.searchCategory(1L).getBody();
        var categoryResponseStatus = categoryController.searchCategory(1L);

        assertThat(categoryResponse)
                .isNull();
        assertThat(categoryResponseStatus.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to save category")
    @Test
    void saveCategory()  {
        var categoryResponse = categoryController.saveCategory(CategoryRequestCreator
                        .createCategoryRequest()).getBody();
        assertThat(categoryResponse)
                .isNotNull();
        assertThat(CategoryCreator.createCategory().getTitle()).isEqualTo(categoryResponse.getTitle());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to change category")
    @Test
    void updateCategory()  {
        var categoryResponse = categoryController.updateCategory(1L, CategoryRequestCreator
                .createCategoryRequest()).getBody();

        assertThat(categoryResponse)
                .isNotNull();
        assertThat(CategoryCreator.createCategory().getId()).isEqualTo(categoryResponse.getId());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to delete category")
    @Test
    void deleteCategory()  {
        assertThatCode(() -> categoryController.deleteCategory(1L)).doesNotThrowAnyException();

        ResponseEntity<Void> entity = categoryController.deleteCategory(1L);

        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}