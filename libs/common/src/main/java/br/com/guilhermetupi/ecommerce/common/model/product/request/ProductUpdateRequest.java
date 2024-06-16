package br.com.guilhermetupi.ecommerce.common.model.product.request;

import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class ProductUpdateRequest {
    private String name;

    private String description;

    private Float price;

    private Set<UUID> categoryIds;

    private String importantInformation;

    private List<ProductCreateProductVersionRequest> productVersions;
}
