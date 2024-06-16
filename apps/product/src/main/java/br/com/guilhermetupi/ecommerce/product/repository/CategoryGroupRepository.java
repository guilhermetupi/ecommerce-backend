package br.com.guilhermetupi.ecommerce.product.repository;

import br.com.guilhermetupi.ecommerce.product.domain.CategoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, UUID> {
}
