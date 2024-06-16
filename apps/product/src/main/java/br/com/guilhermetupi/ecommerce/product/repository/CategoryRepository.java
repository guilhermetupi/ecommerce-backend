package br.com.guilhermetupi.ecommerce.product.repository;

import br.com.guilhermetupi.ecommerce.product.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByCategoryIdIsNull();

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.subCategories WHERE c.id = :categoryId")
    Category findByIdWithSubCategories(@Param("categoryId") UUID categoryId);
}
