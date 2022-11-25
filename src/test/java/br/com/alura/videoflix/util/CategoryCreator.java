package br.com.alura.videoflix.util;

import br.com.alura.videoflix.domain.entity.Category;

public class CategoryCreator {

    public static Category createCategory(){
        return Category.builder()
                .id(1L)
                .title("title1")
                .color("color1")
                .build();
    }
}
