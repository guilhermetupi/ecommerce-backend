package br.com.guilhermetupi.ecommerce.product.controller;

import br.com.guilhermetupi.ecommerce.common.model.product.request.CategoryGroupCreateRequest;
import br.com.guilhermetupi.ecommerce.common.model.product.request.CategoryGroupUpdateRequest;
import br.com.guilhermetupi.ecommerce.common.model.product.response.CategoryGroupResponse;
import br.com.guilhermetupi.ecommerce.product.service.CategoryGroupService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category-groups")
public class CategoryGroupController {
    private final CategoryGroupService categoryGroupService;
    private final ModelMapper modelMapper;

    @PostMapping
    public CategoryGroupResponse create(@RequestBody  CategoryGroupCreateRequest request) {
        return modelMapper.map(categoryGroupService.save(request), CategoryGroupResponse.class);
    }

    @PostMapping("/{categoryGroupId}/category/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCategory(@PathVariable(name = "categoryGroupId") UUID categoryGroupId, @PathVariable(name = "categoryId") UUID categoryId) {
        categoryGroupService.addCategory(categoryGroupId, categoryId);
    }

    @DeleteMapping("/{categoryGroupId}/category/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCategory(@PathVariable(name = "categoryGroupId") UUID categoryGroupId, @PathVariable(name = "categoryId") UUID categoryId) {
        categoryGroupService.removeCategory(categoryGroupId, categoryId);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateName(@PathVariable UUID id, @RequestBody CategoryGroupUpdateRequest request) {
        categoryGroupService.updateName(id, request);
    }
}
