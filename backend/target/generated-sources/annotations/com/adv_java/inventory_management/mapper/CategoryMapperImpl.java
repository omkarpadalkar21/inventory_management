package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.category.CategoryDto;
import com.adv_java.inventory_management.domain.dtos.category.CreateCategoryDto;
import com.adv_java.inventory_management.domain.dtos.category.UpdateCategoryDto;
import com.adv_java.inventory_management.domain.entities.Categories;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-01T22:38:11+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDto toDto(Categories category) {
        if ( category == null ) {
            return null;
        }

        CategoryDto.CategoryDtoBuilder categoryDto = CategoryDto.builder();

        categoryDto.createdAt( category.getCreatedAt() );
        categoryDto.description( category.getDescription() );
        categoryDto.id( category.getId() );
        categoryDto.name( category.getName() );
        categoryDto.updatedAt( category.getUpdatedAt() );

        return categoryDto.build();
    }

    @Override
    public Categories fromCreateDto(CreateCategoryDto dto) {
        if ( dto == null ) {
            return null;
        }

        Categories.CategoriesBuilder categories = Categories.builder();

        categories.description( dto.getDescription() );
        categories.name( dto.getName() );

        return categories.build();
    }

    @Override
    public void updateCategoryFromDto(UpdateCategoryDto dto, Categories category) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getDescription() != null ) {
            category.setDescription( dto.getDescription() );
        }
        if ( dto.getName() != null ) {
            category.setName( dto.getName() );
        }
    }
}
