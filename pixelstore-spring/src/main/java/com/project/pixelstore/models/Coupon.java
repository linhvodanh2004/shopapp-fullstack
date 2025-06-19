package com.project.pixelstore.models;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    // Value refers to sale percent if type is percentage
    // Value refers to sale amount if type is fixed_amount
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
