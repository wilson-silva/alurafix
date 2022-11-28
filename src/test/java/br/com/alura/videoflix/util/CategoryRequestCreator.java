package br.com.alura.videoflix.util;

import br.com.alura.videoflix.api.request.CategoryRequest;

public class CategoryRequestCreator {

    public static CategoryRequest createCategoryRequest(){
        return CategoryRequest.builder()
                .title(CategoryCreator.createCategory().getTitle())
                .color(CategoryCreator.createCategory().getColor())
                .build();

    }

}
