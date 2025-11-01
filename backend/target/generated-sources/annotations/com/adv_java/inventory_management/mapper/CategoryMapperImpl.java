package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.category.CategoryDto;
import com.adv_java.inventory_management.domain.dtos.category.CreateCategoryDto;
import com.adv_java.inventory_management.domain.dtos.category.UpdateCategoryDto;
import com.adv_java.inventory_management.domain.entities.Categories;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-01T22:48:27+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Ubuntu)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto toDto(Categories category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.id( category.getId() );
        categoryDto.name( category.getName() );
        categoryDto.description( category.getDescription() );
        categoryDto.createdAt( category.getCreatedAt() );
        categoryDto.updatedAt( category.getUpdatedAt() );

        return categoryDto.build();
    }

    @Override
    public Categories fromCreateDto(CreateCategoryDto dto) {
        if ( dto == null ) {
            return null;
        }

        Categories.CategoriesBuilder categories = Categories.builder();

        categories.name( dto.getName() );
        categories.description( dto.getDescription() );

        return categories.build();
    }

    @Override
    public void updateCategoryFromDto(UpdateCategoryDto dto, Categories category) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            category.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            category.setDescription( dto.getDescription() );
        }
    }
}
