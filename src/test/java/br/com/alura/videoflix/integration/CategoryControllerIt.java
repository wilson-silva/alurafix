package br.com.alura.videoflix.integration;

import br.com.alura.videoflix.api.request.CategoryRequest;
import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.repository.CategoryRepository;
import br.com.alura.videoflix.domain.repository.VideoRepository;
import br.com.alura.videoflix.exception.BusinessException;
import br.com.alura.videoflix.exception.ConflitException;
import br.com.alura.videoflix.util.CategoryCreator;
import br.com.alura.videoflix.util.CategoryRequestCreator;
import br.com.alura.videoflix.util.VideoCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryControllerIt{

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    VideoRepository videoRepository;
    //------------------------------------------------------------------------------------------

    @BeforeEach
    void up(){

        var savedCategory = categoryRepository.save(CategoryCreator.createCategory1());
        var savedCategory2 = categoryRepository.save(CategoryCreator.createCategory2());
        var savedVideo = videoRepository.save(VideoCreator.createVideo());
    }
    //------------------------------------------------------------------------------------------

    @AfterAll
    void down(){
        videoRepository.deleteAll();
        categoryRepository.deleteAll();
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("List all categories")
    @Test
    void listAllCategories() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andDo(print());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("List all videos by category")
    @Test
    void listAllVideosByCategories() throws Exception {
        mockMvc.perform(get("/categories/{id}/videos", 1L))
                .andExpect(status().isOk())
                .andDo(print());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should not return list of videos by category when category is not found")
    @Test
    void TestListAllVideosByCategoriesWhenCategoryNotFound() throws Exception {
        mockMvc.perform(get("/categories/{id}/videos", 2L))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException()
                        instanceof BusinessException))
                .andDo(print());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should return category")
    @Test
    void searchCategory() throws Exception {
        mockMvc.perform(get("/categories/{id}", 2L))
                .andExpect(status().isOk())
                .andDo(print());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should not return category when category is not found")
    @Test
    void TestSearchCategoryWhenCategoryIsNotFound() throws Exception {
        mockMvc.perform(get("/categories/{id}", 10L))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should save a category")
    @Test
    void saveCategory() throws Exception {

        var category = CategoryRequestCreator.createCategoryRequestNew();

        var categoryRequest = objectMapper.writeValueAsString(category);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(categoryRequest))
                .andExpect(status().isCreated())
                .andDo(print());
    }
    //------------------------------------------------------------------------------------------

    @DisplayName("Should not save a category")
    @Test
    void saveCategoryWithTitleOrColorExistent() throws Exception {

        var category = CategoryRequest.builder()
                .title("title1")
                .color("color1")
                .build();

        var categoryRequest = objectMapper.writeValueAsString(category);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(categoryRequest))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException()
                        instanceof BusinessException))
                .andDo(print());
    }
    //------------------------------------------------------------------------------------------
    @DisplayName("Should update category")
    @Test
    void updateCategory() throws Exception {

        var category = CategoryRequest.builder()
                .title("title_update")
                .color("color_update")
                .build();

        var categoryRequest = objectMapper.writeValueAsString(category);

        mockMvc.perform(put("/categories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(categoryRequest))
                .andExpect(status().isOk())
                .andDo(print());
    }
    //------------------------------------------------------------------------------------------
    @DisplayName("Should not update category")
    @Test
    void TestUpdateCategoryWhenCategoryIsNotFound() throws Exception {

        var category = CategoryRequest.builder()
                .title("title_update")
                .color("color_update")
                .build();

        var categoryRequest = objectMapper.writeValueAsString(category);

        mockMvc.perform(put("/categories/{id}", 6L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(categoryRequest))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException()
                        instanceof BusinessException))
                .andDo(print());
    }
    //------------------------------------------------------------------------------------------
    @DisplayName("Should delete category")
    @Test
    void deleteCategory() throws Exception {

        mockMvc.perform(delete("/categories/{id}", 2L))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
    //------------------------------------------------------------------------------------------
    @DisplayName("Should not delete category")
    @Test
    void TestDeleteCategoryWhenCategoryIsNotFound() throws Exception {

        mockMvc.perform(delete("/categories/{id}", 100L))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException()
                        instanceof BusinessException))
                .andDo(print());
    }

    //------------------------------------------------------------------------------------------
    @DisplayName("Should not delete category when category is used in video")
    @Test
    void TestDeleteCategoryWhenCategoryIsUsed() throws Exception {

        mockMvc.perform(delete("/categories/{id}", 1L))
                .andExpect(status().isConflict())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException()
                        instanceof ConflitException))
                .andDo(print());
    }


}
