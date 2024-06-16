package br.com.guilhermetupi.ecommerce.product.repository;

import br.com.guilhermetupi.ecommerce.product.domain.ProductVersionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductVersionProductRepository extends JpaRepository<ProductVersionProduct, UUID> {
}
