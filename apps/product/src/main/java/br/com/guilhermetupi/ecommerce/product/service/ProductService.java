package br.com.guilhermetupi.ecommerce.product.service;

import br.com.guilhermetupi.ecommerce.common.exception.NotFoundException;
import br.com.guilhermetupi.ecommerce.common.model.product.request.ProductCreateProductVersionRequest;
import br.com.guilhermetupi.ecommerce.common.model.product.request.ProductCreateRequest;
import br.com.guilhermetupi.ecommerce.common.model.product.request.ProductUpdateRequest;
import br.com.guilhermetupi.ecommerce.product.domain.Category;
import br.com.guilhermetupi.ecommerce.product.domain.Product;
import br.com.guilhermetupi.ecommerce.product.domain.ProductVersion;
import br.com.guilhermetupi.ecommerce.product.domain.ProductVersionProduct;
import br.com.guilhermetupi.ecommerce.product.repository.CategoryRepository;
import br.com.guilhermetupi.ecommerce.product.repository.ProductRepository;
import br.com.guilhermetupi.ecommerce.product.repository.ProductVersionProductRepository;
import br.com.guilhermetupi.ecommerce.product.repository.ProductVersionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductVersionRepository productVersionRepository;
    private final ProductVersionProductRepository productVersionProductRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Product save(ProductCreateRequest request) {
        var product = modelMapper.map(request, Product.class);
        product = productRepository.save(product);

        addProductCategories(request.getCategoryIds(), product);
        if(request.getProductVersions() != null && !request.getProductVersions().isEmpty()) {
            setProductVersionProducts(request.getProductVersions());
        }

        return product;
    }

    private void addProductCategories(Set<UUID> categoryIds, Product product) {
        var categories = new ArrayList<Category>();
        for (UUID categoryId : categoryIds) {
            var category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException("categoria " + categoryId));
            categories.add(category);
        }
        for(Category category : categories) {
            category.getProducts().add(product);
            categoryRepository.save(category);
        }
    }

    private void setProductVersionProducts(List<ProductCreateProductVersionRequest> productCreateProductVersions) {
        var productVersions = new ArrayList<ProductVersion>();
        var productVersionProductsArrayList = new ArrayList<ArrayList<String>>();

        var productCreateProductVersionIndex = 0;
        for(ProductCreateProductVersionRequest productCreateProductVersion : productCreateProductVersions) {
            getProductVersionAndAddProductVersionProductsToArrayList(productCreateProductVersion, productVersions, productVersionProductsArrayList, productCreateProductVersionIndex);
        }

        var productVersionIndex = 0;
        for(ProductVersion productVersion : productVersions) {
            saveProductVersionProductsAndAddToProductVersionsRelation(productVersion, productVersionProductsArrayList, productVersionIndex);
        }
    }

    private void getProductVersionAndAddProductVersionProductsToArrayList(ProductCreateProductVersionRequest productCreateProductVersion, ArrayList<ProductVersion> productVersions, ArrayList<ArrayList<String>> productVersionProductsArrayList, int productCreateProductVersionIndex) {
        var productVersion = productVersionRepository.findById(productCreateProductVersion.getProductVersionId())
                .orElseThrow(() -> new NotFoundException("versão de produto " + productCreateProductVersion.getProductVersionId()));
        productVersions.add(productVersion);

        productVersionProductsArrayList.add(new ArrayList<>());
        for(String name : productCreateProductVersion.getNames()) {
            productVersionProductsArrayList.get(productCreateProductVersionIndex).add(name);
        }
    }

    private void saveProductVersionProductsAndAddToProductVersionsRelation(ProductVersion productVersion, ArrayList<ArrayList<String>> productVersionProductsArrayList, int productVersionIndex) {
        for(String name : productVersionProductsArrayList.get(productVersionIndex)) {
            var productVersionProduct = new ProductVersionProduct();
            productVersionProduct.setName(name);
            productVersion.getProductVersionProducts().add(productVersionProductRepository
                    .save(productVersionProduct));
        }
        productVersionRepository.save(productVersion);
    }

    @Transactional
    public void update(UUID id, ProductUpdateRequest request) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("produto " + id));
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(request, product);

        var categoryIds = request.getCategoryIds();
        if(categoryIds != null && !categoryIds.isEmpty()) {
            product.updateCategories(categoryIds);
            addProductCategories(categoryIds, product);
        }

        var productVersions = request.getProductVersions();
        if(productVersions != null && !productVersions.isEmpty()) {
            setProductVersionProducts(productVersions);
        }
    }

    public Set<Product> listByCategory(UUID categoryId) {
        var products = new HashSet<Product>();
        var category = categoryRepository.findByIdWithSubCategories(categoryId);
        if(category != null) {
            getAllProductsFromCategoryAndSubCategories(category, products);
        }
        return products;
    }

    private void getAllProductsFromCategoryAndSubCategories(Category category, HashSet<Product> products) {
        products.addAll(category.getProducts());
        for(Category subCategory : category.getSubCategories()) {
            getAllProductsFromCategoryAndSubCategories(subCategory, products);
        }
    }
}
