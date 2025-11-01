package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.CreateProductDto;
import com.adv_java.inventory_management.domain.dtos.ProductDto;
import com.adv_java.inventory_management.domain.dtos.UpdateProductDto;
import com.adv_java.inventory_management.domain.entities.Categories;
import com.adv_java.inventory_management.domain.entities.Products;
import com.adv_java.inventory_management.domain.entities.Supplier;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-01T22:38:12+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toDto(Products product) {
        if ( product == null ) {
            return null;
        }

        ProductDto.ProductDtoBuilder productDto = ProductDto.builder();

        productDto.categoryId( productCategoryId( product ) );
        productDto.categoryName( productCategoryName( product ) );
        productDto.supplierId( productSupplierId( product ) );
        productDto.supplierName( productSupplierName( product ) );
        productDto.costPrice( product.getCostPrice() );
        productDto.createdAt( product.getCreatedAt() );
        productDto.description( product.getDescription() );
        productDto.id( product.getId() );
        productDto.name( product.getName() );
        productDto.quantityInStock( product.getQuantityInStock() );
        productDto.reorderLevel( product.getReorderLevel() );
        productDto.sku( product.getSku() );
        productDto.status( product.getStatus() );
        productDto.unitPrice( product.getUnitPrice() );
        productDto.updatedAt( product.getUpdatedAt() );

        return productDto.build();
    }

    @Override
    public Products fromCreateDto(CreateProductDto dto) {
        if ( dto == null ) {
            return null;
        }

        Products.ProductsBuilder products = Products.builder();

        products.costPrice( dto.getCostPrice() );
        products.description( dto.getDescription() );
        products.name( dto.getName() );
        products.quantityInStock( dto.getQuantityInStock() );
        products.reorderLevel( dto.getReorderLevel() );
        products.sku( dto.getSku() );
        products.status( dto.getStatus() );
        products.unitPrice( dto.getUnitPrice() );

        return products.build();
    }

    @Override
    public void updateProductFromDto(UpdateProductDto dto, Products product) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCostPrice() != null ) {
            product.setCostPrice( dto.getCostPrice() );
        }
        if ( dto.getDescription() != null ) {
            product.setDescription( dto.getDescription() );
        }
        if ( dto.getName() != null ) {
            product.setName( dto.getName() );
        }
        if ( dto.getQuantityInStock() != null ) {
            product.setQuantityInStock( dto.getQuantityInStock() );
        }
        if ( dto.getReorderLevel() != null ) {
            product.setReorderLevel( dto.getReorderLevel() );
        }
        if ( dto.getSku() != null ) {
            product.setSku( dto.getSku() );
        }
        if ( dto.getStatus() != null ) {
            product.setStatus( dto.getStatus() );
        }
        if ( dto.getUnitPrice() != null ) {
            product.setUnitPrice( dto.getUnitPrice() );
        }
    }

    private UUID productCategoryId(Products products) {
        Categories category = products.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.getId();
    }

    private String productCategoryName(Products products) {
        Categories category = products.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.getName();
    }

    private UUID productSupplierId(Products products) {
        Supplier supplier = products.getSupplier();
        if ( supplier == null ) {
            return null;
        }
        return supplier.getId();
    }

    private String productSupplierName(Products products) {
        Supplier supplier = products.getSupplier();
        if ( supplier == null ) {
            return null;
        }
        return supplier.getName();
    }
}
