package io.github.luaprogrammer.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Long number;

    @Column(nullable = false)
    private Boolean isPrincipal = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personId")
    @ToString.Exclude
    private Person person;
}
