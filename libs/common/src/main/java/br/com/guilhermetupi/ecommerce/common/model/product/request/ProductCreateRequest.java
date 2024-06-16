package br.com.guilhermetupi.ecommerce.common.model.product.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class ProductCreateRequest {
    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull(message = "Preço não pode ser nulo.")
    @DecimalMin(value = "0.01", inclusive = false, message = "Preço deve ser maior que zero.")
    private Float price;

    @NotEmpty
    private Set<UUID> categoryIds;

    private String importantInformation;

    private List<ProductCreateProductVersionRequest> productVersions;
}