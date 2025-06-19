package com.project.pixelstore.models;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "cart_details")
@Data
public class CartDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    @ManyToMany
    @JoinTable(name = "cart_detail_options",
        joinColumns = @JoinColumn(name = "cart_detail_id"),
        inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<Option> options;

    @ManyToMany
    @JoinTable(name = "cart_detail_option_values",
        joinColumns = @JoinColumn(name = "cart_detail_id"),
        inverseJoinColumns = @JoinColumn(name = "option_value_id"))
    private Set<OptionValue> optionValues;

    @Column(name = "calculated_price")
    private Float calculatedPrice;
}
