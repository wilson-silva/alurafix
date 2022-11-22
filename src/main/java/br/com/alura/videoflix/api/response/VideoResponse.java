package br.com.alura.videoflix.api.response;

import lombok.*;

@Getter
@Setter
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
