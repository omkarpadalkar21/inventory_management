package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.supplier.CreateSupplierDto;
import com.adv_java.inventory_management.domain.dtos.supplier.SupplierDto;
import com.adv_java.inventory_management.domain.dtos.supplier.UpdateSupplierDto;
import com.adv_java.inventory_management.domain.entities.Supplier;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-01T22:38:11+0530",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class SupplierMapperImpl implements SupplierMapper {

    @Override
    public SupplierDto toDto(Supplier supplier) {
        if ( supplier == null ) {
            return null;
        }

        SupplierDto.SupplierDtoBuilder supplierDto = SupplierDto.builder();

        supplierDto.address( supplier.getAddress() );
        supplierDto.contactPerson( supplier.getContactPerson() );
        supplierDto.createdAt( supplier.getCreatedAt() );
        supplierDto.email( supplier.getEmail() );
        supplierDto.id( supplier.getId() );
        supplierDto.name( supplier.getName() );
        supplierDto.phone( supplier.getPhone() );
        supplierDto.updatedAt( supplier.getUpdatedAt() );

        return supplierDto.build();
    }

    @Override
    public Supplier fromCreateDto(CreateSupplierDto dto) {
        if ( dto == null ) {
            return null;
        }

        Supplier.SupplierBuilder supplier = Supplier.builder();

        supplier.address( dto.getAddress() );
        supplier.contactPerson( dto.getContactPerson() );
        supplier.email( dto.getEmail() );
        supplier.name( dto.getName() );
        supplier.phone( dto.getPhone() );

        return supplier.build();
    }

    @Override
    public void updateSupplierFromDto(UpdateSupplierDto dto, Supplier supplier) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getAddress() != null ) {
            supplier.setAddress( dto.getAddress() );
        }
        if ( dto.getContactPerson() != null ) {
            supplier.setContactPerson( dto.getContactPerson() );
        }
        if ( dto.getEmail() != null ) {
            supplier.setEmail( dto.getEmail() );
        }
        if ( dto.getName() != null ) {
            supplier.setName( dto.getName() );
        }
        if ( dto.getPhone() != null ) {
            supplier.setPhone( dto.getPhone() );
        }
    }
}
