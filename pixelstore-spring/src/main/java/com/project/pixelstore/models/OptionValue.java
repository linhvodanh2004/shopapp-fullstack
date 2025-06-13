package com.project.pixelstore.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "option_values")
@Data

public class OptionValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "name_vie", nullable = false)
    private String nameVie;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false)
    private Option option;
}
