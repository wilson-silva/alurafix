package br.com.alura.videoflix.api.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class VideoResponse {

    private Long identify;
    private String title;
    private String description;
    private String url;
    private CategoryResponse category;

}
