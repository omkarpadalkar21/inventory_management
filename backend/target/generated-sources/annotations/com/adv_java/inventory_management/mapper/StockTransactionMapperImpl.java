package com.adv_java.inventory_management.mapper;

import com.adv_java.inventory_management.domain.dtos.stock.StockTransactionDto;
import com.adv_java.inventory_management.domain.entities.Products;
import com.adv_java.inventory_management.domain.entities.StockTransactions;
import com.adv_java.inventory_management.domain.entities.User;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-01T22:48:27+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Ubuntu)"
)
@Component
public class StockTransactionMapperImpl implements StockTransactionMapper {

    @Override
    public StockTransactionDto toDto(StockTransactions transaction) {
        if ( transaction == null ) {
            return null;
        }

        StockTransactionDto.StockTransactionDtoBuilder stockTransactionDto = StockTransactionDto.builder();

        stockTransactionDto.productId( transactionProductId( transaction ) );
        stockTransactionDto.productName( transactionProductName( transaction ) );
        stockTransactionDto.performedById( transactionPerformedById( transaction ) );
        stockTransactionDto.performedByUsername( transactionPerformedByUsername( transaction ) );
        stockTransactionDto.id( transaction.getId() );
        stockTransactionDto.transactionType( transaction.getTransactionType() );
        stockTransactionDto.quantity( transaction.getQuantity() );
        stockTransactionDto.referenceNumber( transaction.getReferenceNumber() );
        stockTransactionDto.notes( transaction.getNotes() );
        stockTransactionDto.transactionDate( transaction.getTransactionDate() );

        return stockTransactionDto.build();
    }

    private UUID transactionProductId(StockTransactions stockTransactions) {
        Products product = stockTransactions.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getId();
    }

    private String transactionProductName(StockTransactions stockTransactions) {
        Products product = stockTransactions.getProduct();
        if ( product == null ) {
            return null;
        }
        return product.getName();
    }

    private UUID transactionPerformedById(StockTransactions stockTransactions) {
        User performedBy = stockTransactions.getPerformedBy();
        if ( performedBy == null ) {
            return null;
        }
        return performedBy.getId();
    }

    private String transactionPerformedByUsername(StockTransactions stockTransactions) {
        User performedBy = stockTransactions.getPerformedBy();
        if ( performedBy == null ) {
            return null;
        }
        return performedBy.getUsername();
    }
}
