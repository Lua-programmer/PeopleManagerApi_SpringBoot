package io.github.luaprogrammer.api.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(zipCode, address.zipCode) && Objects.equals(place, address.place) && Objects.equals(city, address.city) && Objects.equals(number, address.number) && Objects.equals(isPrincipal, address.isPrincipal) && Objects.equals(person, address.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zipCode, place, city, number, isPrincipal, person);
    }
}
