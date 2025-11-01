package com.adv_java.inventory_management.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "suppliers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(min = 3, max = 50, message = "Supplier name must be between {min} and {max} characters")
    @Column(name = "supplier_name", nullable = false)
    private String name;

    @Size(min = 2, max = 50, message = "Contact person names must be between {min} and {max} characters")
    @Column(name = "contact_person", nullable = false)
    private String contactPerson;

    @Email
    @Size(max = 100, message = "Email can be at max {max} characters")
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Size(max = 20, message = "Phone no. can be at max {max} characters")
    @Column(name = "phone_no", nullable = false, unique = true)
    private String phone;

    @Size(min = 10, max = 255, message = "Address must be betweeen {min} and {max} characters")
    private String address;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
