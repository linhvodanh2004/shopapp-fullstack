package com.project.pixelstore.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "coupons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Coupon extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "description_vie", columnDefinition = "TEXT")
    private String descriptionVie;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at", nullable = false)
    private LocalDateTime endedAt;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Float value;

    @Column(name = "max_usage")
    private Integer maxUsage;

    @Column(name = "order_money_min")
    private Float orderMoneyMin;

    @Column(name = "order_sale_max")
    private Float orderSaleMax;

    private boolean active;

    @ManyToMany
    @JoinTable(name = "product_coupon",
            joinColumns = @JoinColumn(name = "coupon_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;
}
