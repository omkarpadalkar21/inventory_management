package com.adv_java.inventory_management.domain.dtos.category;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryDto {
    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 50)
    private String name;

    @Size(max = 100)
    private String description;
}

