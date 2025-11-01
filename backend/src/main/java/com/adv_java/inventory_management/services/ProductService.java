package com.adv_java.inventory_management.services;

import com.adv_java.inventory_management.domain.dtos.CreateProductDto;
import com.adv_java.inventory_management.domain.dtos.PageResponseDto;
import com.adv_java.inventory_management.domain.dtos.ProductDto;
import com.adv_java.inventory_management.domain.dtos.UpdateProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    PageResponseDto<ProductDto> getAllProducts(int page, int size);
    ProductDto getProductById(UUID id);
    List<ProductDto> getLowStockProducts();
    List<ProductDto> searchProducts(String keyword);
    ProductDto createProduct(CreateProductDto dto);
    ProductDto updateProduct(UUID id, UpdateProductDto dto);
    void deleteProduct(UUID id);
}