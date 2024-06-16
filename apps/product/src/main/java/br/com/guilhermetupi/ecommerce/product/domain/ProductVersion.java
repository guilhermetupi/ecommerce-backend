package br.com.guilhermetupi.ecommerce.product.domain;

import br.com.guilhermetupi.ecommerce.common.domain.AuditableDomainModel;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "product_version")
public class ProductVersion extends AuditableDomainModel {
    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "productVersion")
    private List<ProductVersionProduct> productVersionProducts;
}
