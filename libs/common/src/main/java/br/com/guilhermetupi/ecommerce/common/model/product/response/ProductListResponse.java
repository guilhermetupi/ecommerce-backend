package br.com.guilhermetupi.ecommerce.common.model.product.response;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductListResponse {
    @NotEmpty
    private String id;

    @NotEmpty
    private String name;

    @NotNull(message = "Preço não pode ser nulo.")
    @DecimalMin(value = "0.01", inclusive = false, message = "Preço deve ser maior que zero.")
    private Float price;
}
