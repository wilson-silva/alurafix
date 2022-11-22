package br.com.alura.videoflix.api.controller;

import br.com.alura.videoflix.api.mapper.CategoryMapper;
import br.com.alura.videoflix.api.mapper.VideoMapper;
import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.repository.CategoryRepository;
import br.com.alura.videoflix.domain.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryMapper categoryMapper;

    @MockBean
    private VideoMapper videoMapper;

    @MockBean
    private CategoryRepository repository;

    @Autowired
    private ObjectMapper objectMapper;
    //------------------------------------------------------------------------------------------

    @DisplayName("tests the category list")
    @Test
    void listAllCategories() throws Exception {
        List<Category> listCategory = new ArrayList<>();
        listCategory.add(Category.builder().id(1L).title("title1").color("color1").build());
        listCategory.add(Category.builder().id(2L).title("title2").color("color2").build());

        when(categoryService.listAllCategories()).thenReturn(listCategory);

        mockMvc.perform(get("/categories")).andExpect(status().isOk());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests the video list search by category")
    @Test
    void listAllVideosByCategories() throws Exception {
        Long ID = 1L;
        String URL = "/categories/{id}/videos";
        var category = Category.builder().id(1L).title("title1").color("color1").build();
        List<Video> videoList = new ArrayList<>();
        videoList.add(Video.builder().identify(1L).title("video2").description("video2")
                .url("video1@video1.com").category(category).build());

        when(categoryService.listVideoByCategory(1L)).thenReturn(videoList);

        mockMvc.perform(get(URL, ID)).andExpect(status().isOk());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests category search by id")
    @Test
    void searchCategory() throws Exception {
        Long ID = 1L;
        String URL = "/categories/{id}";
        var category = Category.builder().id(1L).title("title1").color("color1").build();

        when(categoryService.searchById(ID)).thenReturn(Optional.of(category));

        mockMvc.perform(get(URL, ID)).andExpect(status().isOk());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests when the category is not found")
    @Test
    void searchCategoryWhenTheCategoryIsNotFound() throws Exception {
        Long ID = 1L;
        String URL = "/categories/{id}";

        when(categoryService.searchById(ID)).thenReturn(Optional.empty());

        mockMvc.perform(get(URL, ID)).andExpect(status().isNotFound());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to save category")
    @Test
    void saveCategory() throws Exception {

        var category = Category.builder()
                .id(1L).title("testando controller")
                .color("BLUE").build();

        when(categoryService.toSave(category)).thenReturn(category);

        mockMvc.perform(post("/categories")

                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(category)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to change category")
    @Test
    void updateCategory() throws Exception {

        Long ID = 1L;
        String URL = "/categories/{id}";
        var category = Category.builder()
                .id(1L).title("testando controller")
                .color("BLUE").build();

        when(categoryService.updateCategory(ID, category)).thenReturn(category);

        mockMvc.perform(put(URL, ID)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("tests controller to delete category")
    @Test
    void deleteCategory() throws Exception {
        Long ID = 1L;
        String URL = "/categories/{id}";
        var category = Category.builder()
                .id(ID).title("testando controller")
                .color("BLUE").build();
        when(categoryService.searchById(category.getId())).thenReturn(Optional.of(category));

        mockMvc.perform(MockMvcRequestBuilders
                .delete(URL, ID)
                .contentType("application/json"))
                .andExpect(status().isNoContent());
    }
}