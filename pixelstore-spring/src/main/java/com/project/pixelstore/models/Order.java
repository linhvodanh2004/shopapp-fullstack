package com.project.pixelstore.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
@Data

public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "fullname", length = 100, nullable = false)
    private String fullName;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 100)
    private String phoneNumber;

    @Column(length = 200, nullable = false)
    private String address;
    @Column(length = 100)
    private String state;
    @Column(length = 100)
    private String city;
    @Column(length = 100)
    private String country;
    @Column(length = 200)
    private String postalCode;

    @Column(name = "note", length = 100)
    private String note;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "status")
    private String status;

    @Column(name = "calculated_price")
    private Float calculatedPrice;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "shipping_date")
    private LocalDateTime shippingDate;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "shipping_fee")
    private Float shippingFee;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;
}
