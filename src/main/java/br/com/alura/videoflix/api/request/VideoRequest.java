package br.com.alura.videoflix.api.request;

import br.com.alura.videoflix.domain.entity.Category;
import br.com.alura.videoflix.domain.entity.Video;
import br.com.alura.videoflix.domain.repository.CategoryRepository;
import br.com.alura.videoflix.domain.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoRequest {

    private Long categoryId;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    @URL(message = "invalid URL")
    private String url;

}
