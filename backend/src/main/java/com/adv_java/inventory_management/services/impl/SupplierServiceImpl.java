package com.adv_java.inventory_management.services.impl;

import com.adv_java.inventory_management.domain.dtos.supplier.*;
import com.adv_java.inventory_management.domain.entities.Supplier;
import com.adv_java.inventory_management.mapper.SupplierMapper;
import com.adv_java.inventory_management.repository.SupplierRepository;
import com.adv_java.inventory_management.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    public List<SupplierDto> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(supplierMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDto getSupplierById(UUID id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
        return supplierMapper.toDto(supplier);
    }

    @Override
    @Transactional
    public SupplierDto createSupplier(CreateSupplierDto dto) {
        Supplier supplier = supplierMapper.fromCreateDto(dto);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDto(savedSupplier);
    }

    @Override
    @Transactional
    public SupplierDto updateSupplier(UUID id, UpdateSupplierDto dto) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));

        supplierMapper.updateSupplierFromDto(dto, supplier);
        Supplier updatedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDto(updatedSupplier);
    }

    @Override
    @Transactional
    public void deleteSupplier(UUID id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }
}