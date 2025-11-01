package com.adv_java.inventory_management.domain.dtos.supplier;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateSupplierDto {
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 2, max = 50)
    private String contactPerson;

    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private String phone;

    @Size(min = 10, max = 255)
    private String address;
}
