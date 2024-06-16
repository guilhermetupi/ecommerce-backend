package br.com.guilhermetupi.ecommerce.product.domain;

import br.com.guilhermetupi.ecommerce.common.domain.AuditableDomainModel;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product_version")
public class ProductVersionProduct extends AuditableDomainModel {
    @NotEmpty
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_version_id")
    private ProductVersion productVersion;
}
