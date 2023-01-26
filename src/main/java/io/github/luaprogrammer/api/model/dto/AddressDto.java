package io.github.luaprogrammer.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
