package br.com.alura.videoflix.domain.service;

import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.repository.CategoryRepository;
import br.com.alura.videoflix.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@WebAppConfiguration
class CategoryServiceTest {

    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    private Category category;
    private Video video;
    @Captor
    ArgumentCaptor<Category> categoryCaptor;

    @BeforeEach
    public void setUp() {

        category = Category.builder()
                .id(1L)
                .title("teste")
                .color("BLUE")
                .videos(List.of())
                .build();

        video = Video.builder()
                .identify(1L)
                .title("teste")
                .description("teste")
                .url("teste@teste.com")
                .category(category)
                .build();
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should get all videos")
    @Test
    void testMethodListAll() {
        List<Category> categories = List.of(category);

        when(categoryRepository.findAll()).thenReturn(categories);

        categoryService.listAllCategories();

        verify(categoryRepository, times(2)).findAll();
    }

    //------------------------------------------------------------------------------------------
    @DisplayName("Should return exception when list is empty")
    @Test
    void testMethodListAllWhenListIsEmpty() {
        List<Category> categories = List.of();

        when(categoryRepository.findAll()).thenReturn(categories);

        var ex = assertThrows(BusinessException.class, () ->
                categoryService.listAllCategories(), "list video is not empty");
        assertEquals("empty video list!", ex.getMessage());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should get one categoty")
    @Test
    void searchById() {

        Long id = 1L;

        when(categoryRepository.findById(id))
                .thenReturn(Optional.ofNullable(category));

        categoryService.searchById(id);

        verify(categoryRepository).findById(1L);
        assertEquals(category.getId(), 1L);
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should save videos successfully")
    @Test
    void TestMethodToSave() {

        when(categoryRepository.findByTitleOrColor(category.getTitle(), category.getColor()))
                .thenReturn(Optional.empty());

        categoryService.toSave(category);

        verify(categoryRepository).findByTitleOrColor(category.getTitle(), category.getColor());

        verify(categoryRepository).save(categoryCaptor.capture());
        Category categorySaved = categoryCaptor.getValue();
        assertThat(categorySaved.getId()).isNotNull();
        assertDoesNotThrow(() -> categoryService.toSave(category));
    }

    //------------------------------------------------------------------------------------------
    @Test
    @DisplayName("Should not save category successfully")
    void TestMethodToSaveWhenCategoryAlreadyRegistered() {

        var category3 = Category.builder().id(3L).title("teste3").color("GREEN").build();
        when(categoryRepository.findByTitleOrColor(category.getTitle(), category.getColor()))
                .thenReturn(Optional.of(category3));

        var ex = assertThrows(BusinessException.class, () ->
                categoryService.toSave(category), "saved category");
        assertEquals("category already registered", ex.getMessage());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should test when categoty is update")
    @Test
    void testMethodUpdateCategory() {
        Long id = 1L;

        when(categoryService.searchById(id))
                .thenReturn(Optional.ofNullable(category));

        categoryService.updateCategory(id, category);

        verify(categoryRepository).save(categoryCaptor.capture());
        Category categorySaved = categoryCaptor.getValue();
        assertThat(categorySaved.getId()).isNotNull();
        assertDoesNotThrow(() -> categoryService.updateCategory(id, category));
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should return exception when the category is not found in the update")
    @Test
    void testMethodUpdateCategoryWhenCategoryIsNotFound() {
        Long id = 2L;

        when(categoryService.searchById(id))
                .thenReturn(Optional.empty());

        var ex = assertThrows(BusinessException.class, () ->
                categoryService.updateCategory(id, category), "video found");
        assertEquals("Category not found!", ex.getMessage());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should delete a category")
    @Test
    void testMethodDeleteCategory() {

        when(categoryService.searchById(1L)).thenReturn(Optional.of(category));
        System.out.println(category.getVideos());
        categoryService.deleteCategory(1L);
        verify(categoryRepository).delete(category);

        assertDoesNotThrow(() -> categoryService.deleteCategory(1L));
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should return exception when the category is not found in the delete")
    @Test
    void testMethodDeleteCategoryWhenCategoryIsNotFound() {

        when(categoryService.searchById(category.getId()))
                .thenReturn(Optional.empty());

        var ex = assertThrows(BusinessException.class, () ->
                categoryService.deleteCategory(2L), "categoty found");
        assertEquals("Category not found!", ex.getMessage());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Test method listVideoByCategory")
    @Test
    void TestMethodListVideoByCategory() {

        category.setVideos(List.of(video));

        when(categoryRepository.findById(3L))
                .thenReturn(Optional.of(category));

        assertDoesNotThrow(() -> categoryService.listVideoByCategory(3L));
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should return exception when category not found")
    @Test
    void TestMethodListVideoByCategoryWhenCategoryNotFound() {

        when(categoryRepository.findById(2L))
                .thenReturn(Optional.empty());

        var ex = assertThrows(BusinessException.class, () ->
                        categoryService.listVideoByCategory(2L),
                "category found");
        assertEquals("Category not found!", ex.getMessage());
    }

    //------------------------------------------------------------------------------------------
    @DisplayName("Should return exception when list is empty")
    @Test
    void TestMethodListVideoByCategoryWhenListIsEmpty() {

        var category4 = Category.builder()
                .id(4L)
                .title("Category 4")
                .color("RED")
                .videos(List.of())
                .build();

        when(categoryRepository.findById(4L))
                .thenReturn(Optional.of(category4));

        var ex = assertThrows(BusinessException.class, () ->
                        categoryService.listVideoByCategory(category4.getId()), "List is not empty");
        assertEquals("There is no video for the given category!", ex.getMessage());
    }

}