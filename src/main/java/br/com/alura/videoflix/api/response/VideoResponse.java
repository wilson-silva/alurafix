package br.com.alura.videoflix.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;@Data
@AllArgsConstructor
@NoArgsConstructor


public class VideoResponse {
    private Long id;
    private String title;
    private String description;
    private String url;
}
