package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.supplier.CreateSupplierDto;
import com.adv_java.inventory_management.domain.dtos.supplier.SupplierDto;
import com.adv_java.inventory_management.domain.dtos.supplier.UpdateSupplierDto;
import com.adv_java.inventory_management.domain.entities.Supplier;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-01T19:29:55+0530",
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

        supplierDto.contactPerson( supplier.getContact_person() );
        supplierDto.id( supplier.getId() );
        supplierDto.name( supplier.getName() );
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

        supplier.contact_person( dto.getContactPerson() );
        supplier.name( dto.getName() );
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

        if ( dto.getContactPerson() != null ) {
            supplier.setContact_person( dto.getContactPerson() );
        }
        if ( dto.getName() != null ) {
            supplier.setName( dto.getName() );
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
