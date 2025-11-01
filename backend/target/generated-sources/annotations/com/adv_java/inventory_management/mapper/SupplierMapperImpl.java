package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.supplier.CreateSupplierDto;
import com.adv_java.inventory_management.domain.dtos.supplier.SupplierDto;
import com.adv_java.inventory_management.domain.dtos.supplier.UpdateSupplierDto;
import com.adv_java.inventory_management.domain.entities.Supplier;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-01T22:48:27+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Ubuntu)"
)
@Component
public class SupplierMapperImpl implements SupplierMapper {

    @Override
    public SupplierDto toDto(Supplier supplier) {
        if ( supplier == null ) {
            return null;
        }

        SupplierDto.SupplierDtoBuilder supplierDto = SupplierDto.builder();

        supplierDto.id( supplier.getId() );
        supplierDto.name( supplier.getName() );
        supplierDto.contactPerson( supplier.getContactPerson() );
        supplierDto.email( supplier.getEmail() );
        supplierDto.phone( supplier.getPhone() );
        supplierDto.address( supplier.getAddress() );
        supplierDto.createdAt( supplier.getCreatedAt() );
        supplierDto.updatedAt( supplier.getUpdatedAt() );

        return supplierDto.build();
    }

    @Override
    public Supplier fromCreateDto(CreateSupplierDto dto) {
        if ( dto == null ) {
            return null;
        }

        Supplier.SupplierBuilder supplier = Supplier.builder();

        supplier.name( dto.getName() );
        supplier.contactPerson( dto.getContactPerson() );
        supplier.email( dto.getEmail() );
        supplier.phone( dto.getPhone() );
        supplier.address( dto.getAddress() );

        return supplier.build();
    }

    @Override
    public void updateSupplierFromDto(UpdateSupplierDto dto, Supplier supplier) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            supplier.setName( dto.getName() );
        }
        if ( dto.getContactPerson() != null ) {
            supplier.setContactPerson( dto.getContactPerson() );
        }
        if ( dto.getEmail() != null ) {
            supplier.setEmail( dto.getEmail() );
        }
        if ( dto.getPhone() != null ) {
            supplier.setPhone( dto.getPhone() );
        }
        if ( dto.getAddress() != null ) {
            supplier.setAddress( dto.getAddress() );
        }
    }
}
