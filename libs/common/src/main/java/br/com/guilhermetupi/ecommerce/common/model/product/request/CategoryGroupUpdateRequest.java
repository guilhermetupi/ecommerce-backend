package br.com.guilhermetupi.ecommerce.common.model.product.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryGroupUpdateRequest {
    @NotEmpty
    private String name;
}
