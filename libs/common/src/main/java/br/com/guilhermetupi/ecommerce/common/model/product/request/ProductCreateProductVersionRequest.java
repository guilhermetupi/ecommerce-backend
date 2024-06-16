package br.com.guilhermetupi.ecommerce.common.model.product.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class ProductCreateProductVersionRequest {
    @NotEmpty
    private UUID productVersionId;

    @NotEmpty
    private Set<String> names;
}
