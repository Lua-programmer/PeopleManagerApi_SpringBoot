package io.github.luaprogrammer.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

    private Long id;

    private String zipCode;

    private String place;

    private String city;

    private Long number;

    private Long personId;
}
