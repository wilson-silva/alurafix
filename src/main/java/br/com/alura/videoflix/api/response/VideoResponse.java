package br.com.alura.videoflix.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoResponse {

    private Long identify;
    private String title;
    private String description;
    private String url;
    private CategoryResponse category;

}
