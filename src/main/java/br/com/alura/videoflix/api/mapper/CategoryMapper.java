package br.com.alura.videoflix.api.mapper;

import br.com.alura.videoflix.api.request.CategoryRequest;
import br.com.alura.videoflix.api.response.CategoryResponse;
import br.com.alura.videoflix.domain.entity.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapper {

    private final ModelMapper mapper;

    public Category toCategory(CategoryRequest request) {
        return mapper.map(request, Category.class);
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return mapper.map(category, CategoryResponse.class);
    }

    public List<CategoryResponse> toCategoryResponseList(List<Category> categories) {
        return categories.stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }
}
