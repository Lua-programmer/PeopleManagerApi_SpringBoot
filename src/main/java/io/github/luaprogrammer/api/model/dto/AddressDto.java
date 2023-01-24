package io.github.luaprogrammer.api.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

    private Long id;

    @NotBlank(message = "CEP ")
    private String zipCode;

    @NotBlank(message = "Logradouro ")
    private String place;

    @NotBlank(message = "Cidade ")
    private String city;
    @NotNull(message = "Número da casa ")
    private Long number;

    private Boolean isPrincipal;

    private Long personId;
}
