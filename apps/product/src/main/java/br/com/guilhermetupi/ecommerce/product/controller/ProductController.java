package br.com.guilhermetupi.ecommerce.product.controller;

import br.com.guilhermetupi.ecommerce.common.model.product.request.ProductCreateRequest;
import br.com.guilhermetupi.ecommerce.common.model.product.request.ProductUpdateRequest;
import br.com.guilhermetupi.ecommerce.common.model.product.response.ProductCreateResponse;
import br.com.guilhermetupi.ecommerce.common.model.product.response.ProductListResponse;
import br.com.guilhermetupi.ecommerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCreateResponse create(@RequestBody ProductCreateRequest request) {
        return modelMapper.map(productService.save(request), ProductCreateResponse.class);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable UUID id, @RequestBody ProductUpdateRequest request) {
        productService.update(id, request);
    }

    @GetMapping("/categories/{categoryId}")
    public List<ProductListResponse> listByCategory(@PathVariable UUID categoryId) {
        return productService.listByCategory(categoryId)
                .stream().map(p -> modelMapper.map(p, ProductListResponse.class)).toList();
    }
}
