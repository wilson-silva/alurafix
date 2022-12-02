package br.com.alura.videoflix.util;

import br.com.alura.videoflix.api.response.CategoryResponse;

public class CategoryResponseCreator {

    public static CategoryResponse createCategoryResponse(){
        return CategoryResponse.builder()
                .id(CategoryCreator.createCategory1().getId())
                .title(CategoryCreator.createCategory1().getTitle())
                .color(CategoryCreator.createCategory1().getColor())
                .build();

    }
}
