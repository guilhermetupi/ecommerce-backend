package br.com.guilhermetupi.ecommerce.common.model.product.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CategoryGroupCreateRequest {
    @NotEmpty
    private String name;
}
