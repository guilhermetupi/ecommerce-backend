package br.com.guilhermetupi.ecommerce.product.service;

import br.com.guilhermetupi.ecommerce.common.exception.InvalidParameterException;
import br.com.guilhermetupi.ecommerce.common.exception.NotFoundException;
import br.com.guilhermetupi.ecommerce.common.model.product.request.CategoryGroupCreateRequest;
import br.com.guilhermetupi.ecommerce.common.model.product.request.CategoryGroupUpdateRequest;
import br.com.guilhermetupi.ecommerce.product.domain.Category;
import br.com.guilhermetupi.ecommerce.product.domain.CategoryGroup;
import br.com.guilhermetupi.ecommerce.product.repository.CategoryGroupRepository;
import br.com.guilhermetupi.ecommerce.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class CategoryGroupService {
    private final CategoryGroupRepository categoryGroupRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryGroup save(CategoryGroupCreateRequest request) {
        return categoryGroupRepository.save(modelMapper.map(request, CategoryGroup.class));
    }

    public void addCategory(UUID categoryGroupId, UUID categoryId) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("categoria " + categoryId));
        var categoryGroup = categoryGroupRepository.findById(categoryGroupId)
                .orElseThrow(() -> new NotFoundException("grupo de categorias " + categoryGroupId));
        var categories = categoryGroup.getCategories().stream().filter(c -> c.getId() == categoryId);
        if (categories.count() != 0) {
            throw new InvalidParameterException("categoria " + categoryId + " já existe no grupo de categorias " + categoryGroupId);
        }
        categoryGroup.getCategories().add(category);
        categoryGroupRepository.save(categoryGroup);
    }

    public void removeCategory(UUID categoryGroupId, UUID categoryId) {
        var categoryGroup = categoryGroupRepository.findById(categoryGroupId)
                .orElseThrow(() -> new NotFoundException("grupo de categorias " + categoryGroupId));
        var categories = categoryGroup.getCategories()
                .stream().filter(c -> c.getId().equals(categoryId)).toList();
        if (categories.isEmpty()) {
            throw new NotFoundException("categoria " + categoryId + " no grupo de categorias " + categoryGroupId);
        }
        var category = categories.getFirst();
        category.removeFromCategoryGroup(categoryGroupId);
        categoryRepository.save(category);
    }

    public void updateName(UUID id, CategoryGroupUpdateRequest request) {
        var categoryGroup = categoryGroupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("grupo de categorias " + id));
        categoryGroup.setName(request.getName());
        categoryGroupRepository.save(categoryGroup);
    }
}
