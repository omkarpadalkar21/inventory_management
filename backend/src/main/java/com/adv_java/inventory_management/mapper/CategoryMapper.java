package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.category.CategoryDto;
import com.adv_java.inventory_management.domain.dtos.category.CreateCategoryDto;
import com.adv_java.inventory_management.domain.dtos.category.UpdateCategoryDto;
import com.adv_java.inventory_management.domain.entities.Categories;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryDto toDto(Categories category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Categories fromCreateDto(CreateCategoryDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategoryFromDto(UpdateCategoryDto dto, @MappingTarget Categories category);
}
