package br.com.alura.videoflix.api.request;

import br.com.alura.videoflix.domain.enums.Cor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String cor;
}
