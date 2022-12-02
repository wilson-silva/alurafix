package br.com.alura.videoflix.util;

import br.com.alura.videoflix.domain.entity.Category;

public class CategoryCreator {

    public static Category createCategory1(){
        return Category.builder()
                .id(1L)
                .title("title1")
                .color("color1")
                .build();
    }

    public static Category createCategory2(){
        return Category.builder()
                .id(2L)
                .title("title2")
                .color("color2")
                .build();
    }
}
