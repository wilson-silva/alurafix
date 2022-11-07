package br.com.alura.videoflix.api.controller;

import br.com.alura.videoflix.api.mapper.CategoryMapper;
import br.com.alura.videoflix.api.mapper.VideoMapper;
import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listAllCategories() throws Exception {
        List<Category> listCategory = new ArrayList<>();
        listCategory.add(Category.builder().id(1L).title("title1").color("color1").build());
        listCategory.add(Category.builder().id(2L).title("title2").color("color2").build());

        when(categoryService.listAllCategories()).thenReturn(listCategory);

        mockMvc.perform(get("/categories")).andExpect(status().isOk());
    }

    @Test
    void listAllVideosByCategories() throws Exception {
        Long USER_ID = 1L;
        String USER_URL = "/categories/{id}/videos";
        var category = Category.builder().id(1L).title("title1").color("color1").build();
        List<Video> videoList = new ArrayList<>();
        videoList.add(Video.builder().identify(1L).title("video2").description("video2")
                .url("video1@video1.com").category(category).build());

        when(categoryService.listVideoByCategory(1L)).thenReturn(videoList);

        mockMvc.perform(get(USER_URL, USER_ID))
                        .andExpect(status().isOk());
    }

    @Test
    void searchCategory() {
    }

    @Test
    void saveCategory() throws Exception {

        Category category = Category.builder()
                .id(1L).title("testando controller")
                .color("BLUE").build();

        mockMvc.perform(post("/categories")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }
}