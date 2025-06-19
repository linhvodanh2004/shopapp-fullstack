package com.project.pixelstore.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "products")
@Data

public class Product extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 350)
    private String name;

    @Column(name = "name_vie", length = 350)
    private String nameVie;

    private Float price;
    private Float discount;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "sold")
    private Integer sold;

    @Column(length = 300)
    private String thumbnail;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "description_vie", columnDefinition = "TEXT")
    private String descriptionVie;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private boolean active;

    @OneToMany(mappedBy = "product")
    private Set<ProductImage> productImages;

    @ManyToMany(mappedBy = "products")
    private Set<Coupon> coupons;

    @OneToMany(mappedBy = "product")
    private Set<Option> options;
}
