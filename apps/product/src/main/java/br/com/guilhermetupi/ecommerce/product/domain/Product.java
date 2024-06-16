package br.com.guilhermetupi.ecommerce.product.domain;

import br.com.guilhermetupi.ecommerce.common.domain.AuditableDomainModel;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product extends AuditableDomainModel {
    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull(message = "Preço não pode ser nulo.")
    @DecimalMin(value = "0.01", inclusive = false, message = "Preço deve ser maior que zero.")
    private Float price;

    private String importantInformation;

    @OneToMany(mappedBy = "product")
    private List<ProductVersionProduct> productVersionProducts;

    @ManyToMany(mappedBy = "products")
    private Set<Category> categories;

    public void updateCategories(Set<UUID> newCategoryIds) {
        for(Category c : categories) {
            var id = c.getId();
            if(!newCategoryIds.contains(id)) {
                c.getProducts().remove(this);
                this.getCategories().remove(c);
            } else {
                newCategoryIds.remove(id);
            }
        }
    }
}
