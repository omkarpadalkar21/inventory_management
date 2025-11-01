package com.adv_java.inventory_management.domain.dtos.supplier;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSupplierDto {
    @NotBlank(message = "Supplier name is required")
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank(message = "Contact person is required")
    @Size(min = 2, max = 50)
    private String contactPerson;

    @NotBlank(message = "Email is required")
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(max = 20)
    private String phone;

    @Size(min = 10, max = 255)
    private String address;
}

