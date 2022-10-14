package br.com.alura.videoflix.api.mapper;

import br.com.alura.videoflix.api.request.CategoriaRequest;
import br.com.alura.videoflix.api.response.CategoriaResponse;
import br.com.alura.videoflix.domain.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriaMapper {

    public static Category toCategoria(CategoriaRequest request) {
        Category category = new Category();
        category.setColor(request.getCor());
        category.setTitle(request.getTitle());
        return category;
    }

    public static CategoriaResponse toCategoriaResponse(Category category) {
        CategoriaResponse response = new CategoriaResponse();
        response.setId(category.getId());
        response.setCor(category.getColor());
        response.setTitle(category.getTitle());
        return response;
    }

    public static List<CategoriaResponse> toCategoriaResponseList(List<Category> categories) {
        List<CategoriaResponse> responses = new ArrayList<>();
        for (Category category : categories){
            responses.add(toCategoriaResponse(category));
        }
        return responses;
    }
}
