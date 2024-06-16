package br.com.guilhermetupi.ecommerce.common.model.product.response;

import lombok.Data;

import java.util.Optional;
import java.util.UUID;

@Data
public class CategoryWithCategoryResponse {
    private UUID id;
    private String name;
    private Optional<CategoryWithCategoryResponse> category;
}
