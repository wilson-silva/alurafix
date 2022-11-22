package br.com.alura.videoflix.util;

import br.com.alura.videoflix.api.request.VideoRequest;
import br.com.alura.videoflix.api.response.CategoryResponse;
import br.com.alura.videoflix.api.response.VideoResponse;
import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.entity.Video;

public class VideoAndCategoryCreator {

    public static VideoRequest createVideoToBeSave(){
        return VideoRequest.builder()
                .title("video1")
                .description("video1")
                .url("video1@video1.com")
                .categoryId(1L)
                .build();
    }

    public static Category createCategory(){
        return Category.builder()
                .id(1L)
                .title("title1")
                .color("color1")
                .build();
    }

    public static Video createVideo(){
       return Video.builder()
               .identify(1L)
               .title("video1")
               .description("description1")
               .url("video1@video1.com")
               .category(createCategory()).build();
    }

    public static CategoryResponse createCategoryResponse(){
        return CategoryResponse.builder()
                .id(createCategory().getId())
                .title(createCategory().getTitle())
                .color(createCategory().getColor())
                .build();
    }

    public static VideoResponse createVideoResponse(){
        return  VideoResponse.builder()
                .identify(1L)
                .title("video1")
                .description("description1")
                .url("video1@video1.com")
                .category(createCategoryResponse())
                .build();
    }

}
