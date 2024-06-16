package br.com.guilhermetupi.ecommerce.common.model.product.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductCreateResponse {
    @NotEmpty
    private UUID id;

    @NotEmpty
    private String name;
}
