package br.com.alura.videoflix.util;

import br.com.alura.videoflix.api.response.CategoryResponse;

public class CategoryResponseCreator {

    public static CategoryResponse createCategoryResponse(){
        return CategoryResponse.builder()
                .id(CategoryCreator.createCategory().getId())
                .title(CategoryCreator.createCategory().getTitle())
                .color(CategoryCreator.createCategory().getColor())
                .build();

    }
}
