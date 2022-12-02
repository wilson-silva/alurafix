package br.com.alura.videoflix.util;

import br.com.alura.videoflix.api.request.VideoRequest;
import br.com.alura.videoflix.api.response.VideoResponse;
import br.com.alura.videoflix.domain.entity.Video;

public class VideoCreator {

    public static VideoRequest createVideoRequest(){
        return VideoRequest.builder()
                .title("video1")
                .description("video1")
                .url("video1@video1.com")
                .categoryId(1L)
                .build();
    }

    public static Video createVideo(){
       return Video.builder()
               .identify(1L)
               .title("video1")
               .description("description1")
               .url("video1@video1.com")
               .category(CategoryCreator.createCategory1())
               .build();
    }



    public static VideoResponse createVideoResponse(){
        return  VideoResponse.builder()
                .identify(1L)
                .title("video1")
                .description("description1")
                .url("video1@video1.com")
                .category(CategoryResponseCreator.createCategoryResponse())
                .build();
    }

}
