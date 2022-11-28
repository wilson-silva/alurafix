package br.com.alura.videoflix.util;

import br.com.alura.videoflix.api.response.CategoryResponse;
import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.entity.Video;

import java.util.List;

public class ListCategoryCreator {
    public static List<Category> createListCategory(){
        return List.of(CategoryCreator.createCategory());
    }

    public static List<CategoryResponse> createListCategoryResponse(){
        return List.of(CategoryResponseCreator.createCategoryResponse());
    }
}
