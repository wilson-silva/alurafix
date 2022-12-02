package br.com.alura.videoflix.util;

import br.com.alura.videoflix.api.response.CategoryResponse;
import br.com.alura.videoflix.domain.entity.Category;

import java.util.List;

public class ListCategoryCreator {
    public static List<Category> createListCategory(){
        return List.of(CategoryCreator.createCategory1());
    }

    public static List<CategoryResponse> createListCategoryResponse(){
        return List.of(CategoryResponseCreator.createCategoryResponse());
    }
}
