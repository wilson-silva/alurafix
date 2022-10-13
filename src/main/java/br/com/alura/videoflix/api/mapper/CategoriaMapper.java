package br.com.alura.videoflix.api.mapper;

import br.com.alura.videoflix.api.request.CategoriaRequest;
import br.com.alura.videoflix.api.response.CategoriaResponse;
import br.com.alura.videoflix.domain.entity.Categoria;
import br.com.alura.videoflix.domain.enums.Cor;

import java.util.ArrayList;
import java.util.List;

public class CategoriaMapper {

    public static Categoria toCategoria(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setCor(Cor.valueOf(request.getCor()));
        categoria.setTitle(request.getTitle());
        return categoria;
    }

    public static CategoriaResponse toCategoriaResponse(Categoria categoria) {
        CategoriaResponse response = new CategoriaResponse();
        response.setId(categoria.getId());
        response.setCor(categoria.getCor().getDescricao());
        response.setTitle(categoria.getTitle());
        return response;
    }

    public static List<CategoriaResponse> toCategoriaResponseList(List<Categoria> categorias) {
        List<CategoriaResponse> responses = new ArrayList<>();
        for (Categoria categoria : categorias){
            responses.add(toCategoriaResponse(categoria));
        }
        return responses;
    }
}
