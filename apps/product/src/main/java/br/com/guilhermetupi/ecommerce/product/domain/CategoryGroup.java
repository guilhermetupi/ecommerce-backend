package br.com.guilhermetupi.ecommerce.product.domain;

import br.com.guilhermetupi.ecommerce.common.domain.AuditableDomainModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "category_group")
public class CategoryGroup extends AuditableDomainModel {
    @NotEmpty
    private String name;

    @ManyToMany
    @JoinTable(
            name = "category_group_category",
            joinColumns = @JoinColumn(name = "category_group_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    List<Category> categories;
}
