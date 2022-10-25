package br.com.alura.videoflix.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Color is required")
    private String color;
}
