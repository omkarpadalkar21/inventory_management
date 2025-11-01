package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.supplier.CreateSupplierDto;
import com.adv_java.inventory_management.domain.dtos.supplier.SupplierDto;
import com.adv_java.inventory_management.domain.dtos.supplier.UpdateSupplierDto;
import com.adv_java.inventory_management.domain.entities.Supplier;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SupplierMapper {

    @Mapping(source = "contact_person", target = "contactPerson")
    SupplierDto toDto(Supplier supplier);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "contactPerson", target = "contact_person")
    Supplier fromCreateDto(CreateSupplierDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "contactPerson", target = "contact_person")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSupplierFromDto(UpdateSupplierDto dto, @MappingTarget Supplier supplier);
}

