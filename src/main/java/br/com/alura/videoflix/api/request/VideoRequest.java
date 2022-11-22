package br.com.alura.videoflix.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoRequest {

    @NotBlank(message = "The title is mandatory")
    private String title;

    @NotBlank(message = "The description is mandatory")
    private String description;

    @NotBlank(message = "URL is mandatory")
    @URL(message = "invalid URL")
    private String url;

    @NotNull
    private Long categoryId;

}
