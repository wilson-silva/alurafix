package br.com.alura.videoflix.util;

import br.com.alura.videoflix.api.request.CategoryRequest;

public class CategoryRequestCreator {

    public static CategoryRequest createCategoryRequest(){
        return CategoryRequest.builder()
                .title(CategoryCreator.createCategory1().getTitle())
                .color(CategoryCreator.createCategory1().getColor())
                .build();

    }

    public static CategoryRequest createCategoryRequestNew(){
        return CategoryRequest.builder()
                .title("title3")
                .color("color3")
                .build();

    }

}
