package br.com.guilhermetupi.ecommerce.product.controller;

import br.com.guilhermetupi.ecommerce.common.model.product.request.CategoryCreateRequest;
import br.com.guilhermetupi.ecommerce.common.model.product.request.CategoryUpdateRequest;
import br.com.guilhermetupi.ecommerce.common.model.product.response.CategoryWithCategoriesResponse;
import br.com.guilhermetupi.ecommerce.common.model.product.response.CategoryWithCategoryResponse;
import br.com.guilhermetupi.ecommerce.product.domain.Category;
import br.com.guilhermetupi.ecommerce.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryWithCategoryResponse create(@RequestBody CategoryCreateRequest request) {
        return modelMapper.map(categoryService.save(request), CategoryWithCategoryResponse.class);
    }

    @GetMapping
    public List<CategoryWithCategoriesResponse> list() {
        List<Category> categories = categoryService.findAll();
        return categories.stream()
                .map(this::convertToCategoryWithCategoriesResponse)
                .toList();
    }

    private CategoryWithCategoriesResponse convertToCategoryWithCategoriesResponse(Category category) {
        var response = modelMapper.map(category, CategoryWithCategoriesResponse.class);
        if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()) {
            response.setSubCategories(
                    Optional.of(category.getSubCategories().stream()
                            .map(this::convertToCategoryWithCategoriesResponse).toList())
            );
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID id) {
        categoryService.remove(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateName(@PathVariable UUID id, @RequestBody CategoryUpdateRequest request) {
        categoryService.updateName(id, request);
    }
}
