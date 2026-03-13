package com.abhishekvermaa10.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UpdatePetDTO {

    @Size(max = 255, message = "{pet.name.length}")
    @NotBlank(message = "{pet.name.required}")
    private String name;

}
