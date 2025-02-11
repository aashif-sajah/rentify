package com.rentify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Business
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String businessName;

    @Column(nullable = false)
    private String businessType;

    private String description;
    private String address;
    private String contactEmail;
    private String phone;

    @Column(unique = true, nullable = false)
    private String storeSlug; // Unique store URL identifier

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "userId", nullable = false)
    private Users owner;

    @OneToOne(mappedBy = "business", cascade = CascadeType.ALL)
    private StoreTheme storeTheme;
}
