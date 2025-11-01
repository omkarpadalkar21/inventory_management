package com.adv_java.inventory_management.services;

import com.adv_java.inventory_management.domain.dtos.supplier.*;
import java.util.List;
import java.util.UUID;

public interface SupplierService {
    List<SupplierDto> getAllSuppliers();
    SupplierDto getSupplierById(UUID id);
    SupplierDto createSupplier(CreateSupplierDto dto);
    SupplierDto updateSupplier(UUID id, UpdateSupplierDto dto);
    void deleteSupplier(UUID id);
}
