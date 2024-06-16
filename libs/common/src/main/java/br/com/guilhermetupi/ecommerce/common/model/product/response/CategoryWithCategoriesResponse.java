package br.com.guilhermetupi.ecommerce.common.model.product.response;

import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
public class CategoryWithCategoriesResponse {
    private UUID id;
    private String name;
    private Optional<List<CategoryWithCategoriesResponse>> subCategories;
}
