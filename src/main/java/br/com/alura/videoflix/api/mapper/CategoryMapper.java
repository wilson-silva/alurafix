package br.com.alura.videoflix.api.mapper;

import br.com.alura.videoflix.api.request.CategoryRequest;
import br.com.alura.videoflix.api.response.CategoryResponse;
import br.com.alura.videoflix.domain.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static Category toCategory(CategoryRequest request) {
        Category category = new Category();
        category.setColor(request.getColor());
        category.setTitle(request.getTitle());
        return category;
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setColor(category.getColor());
        response.setTitle(category.getTitle());
        return response;
    }

    public static List<CategoryResponse> toCategoryResponseList(List<Category> categories) {
        List<CategoryResponse> responses = new ArrayList<>();
        for (Category category : categories){
            responses.add(toCategoryResponse(category));
        }
        return responses;
    }
}
