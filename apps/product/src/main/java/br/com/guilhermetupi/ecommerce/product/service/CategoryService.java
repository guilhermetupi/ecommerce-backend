package br.com.guilhermetupi.ecommerce.product.service;

import br.com.guilhermetupi.ecommerce.common.exception.NotFoundException;
import br.com.guilhermetupi.ecommerce.common.model.product.request.CategoryCreateRequest;
import br.com.guilhermetupi.ecommerce.common.model.product.request.CategoryUpdateRequest;
import br.com.guilhermetupi.ecommerce.product.domain.Category;
import br.com.guilhermetupi.ecommerce.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public Category save(CategoryCreateRequest request) {
        if(request.getCategoryId() != null) {
            var category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("categoria " + request.getCategoryId()));
            var newCategory = new Category();
            newCategory.setName(request.getName());
            newCategory.setCategory(category);
            return categoryRepository.save(newCategory);
        }

        return categoryRepository.save(modelMapper.map(request, Category.class));
    }

    public List<Category> findAll() {
        return categoryRepository.findByCategoryIdIsNull();
    }

    public void remove(UUID id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("categoria " + id));
        categoryRepository.delete(category);
    }

    public void updateName(UUID id, CategoryUpdateRequest request) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("categoria " + id));
        category.setName(request.getName());
        categoryRepository.save(category);
    }
}
