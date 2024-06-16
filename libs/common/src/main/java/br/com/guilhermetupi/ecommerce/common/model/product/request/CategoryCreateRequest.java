package br.com.guilhermetupi.ecommerce.common.model.product.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryCreateRequest {
    @NotEmpty
    private String name;

    private UUID categoryId;
}
