package br.com.alura.videoflix.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    @URL(message = "invalid URL")
    private String url;
}
