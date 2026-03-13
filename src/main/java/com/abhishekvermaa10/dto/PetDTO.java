package com.abhishekvermaa10.dto;

import com.abhishekvermaa10.enums.Gender;
import com.abhishekvermaa10.enums.PetType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "petCategory"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DomesticPetDTO.class, name = "DOMESTIC"),
        @JsonSubTypes.Type(value = WildPetDTO.class, name = "WILD")
})
public abstract class PetDTO {

    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank(message = "{pet.name.required}")
    @Size(max = 255, message = "{pet.name.length}")
    private String name;

    @NotNull(message = "{pet.gender.required}")
    private Gender gender;

    @NotNull(message = "{pet.type.required}")
    private PetType type;

    private OwnerDTO ownerDTO;

}
