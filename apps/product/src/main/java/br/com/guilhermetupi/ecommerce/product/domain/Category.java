package br.com.guilhermetupi.ecommerce.product.domain;

import br.com.guilhermetupi.ecommerce.common.domain.AuditableDomainModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "category")
public class Category extends AuditableDomainModel {
    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Category> subCategories;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany(mappedBy = "categories")
    private Set<CategoryGroup> categoryGroups;

    @ManyToMany
    @JoinTable(
            name = "category_product",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    Set<Product> products;

    @PreRemove
    private void removeFromCategoryGroupsAndChildCategories() {
        for(Category c : subCategories) {
            if(this.getCategory() == null) {
                c.setCategory(null);
            } else {
                c.setCategory(this.getCategory());
            }
        }

        for(CategoryGroup cg : categoryGroups) {
            cg.getCategories().remove(this);
        }

        for(Product p : products) {
            p.getCategories().remove(this);
        }
    }

    public void removeFromCategoryGroup(UUID categoryGroupId) {
        for(CategoryGroup cg : categoryGroups) {
            if(cg.getId().equals(categoryGroupId)) {
                cg.getCategories().remove(this);
            }
        }
    }
}
