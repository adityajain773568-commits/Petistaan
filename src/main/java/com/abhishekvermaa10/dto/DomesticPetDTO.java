package com.abhishekvermaa10.dto;

import com.abhishekvermaa10.enums.Gender;
import com.abhishekvermaa10.enums.PetType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @author abhishekvermaa10
 */
@NoArgsConstructor
@ToString(callSuper = true)
@Setter
@Getter
public class DomesticPetDTO extends PetDTO {

    @NotNull(message = "{pet.birth.date.required}")
    @PastOrPresent(message = "{pet.birth.date.old}")
    private LocalDate birthDate;

    @Builder
    public DomesticPetDTO(int id, String name, Gender gender, PetType type, OwnerDTO ownerDTO, LocalDate birthDate) {
        super(id, name, gender, type, ownerDTO);
        this.birthDate = birthDate;
    }

}
