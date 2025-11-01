package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.stock.StockTransactionDto;
import com.adv_java.inventory_management.domain.entities.StockTransactions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockTransactionMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "performedBy.id", target = "performedById")
    @Mapping(source = "performedBy.username", target = "performedByUsername")
    StockTransactionDto toDto(StockTransactions transaction);
}

