package br.com.alura.videoflix.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "The field is mandatory")
    private String title;

    @NotBlank(message = "The field is mandatory")
    private String color;
}
