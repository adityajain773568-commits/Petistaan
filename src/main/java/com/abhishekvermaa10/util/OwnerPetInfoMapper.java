package com.abhishekvermaa10.util;

import com.abhishekvermaa10.dto.OwnerPetInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OwnerPetInfoMapper {

    default OwnerPetInfoDTO mapObjectArrayToOwnerPetInfoDTO(Object[] array) {
        String INVALID_ARRAY = "Invalid Object Array Provided";
        if (Objects.isNull(array) || array.length < 4) {
            throw new IllegalArgumentException(INVALID_ARRAY);

        } else {
            return OwnerPetInfoDTO.builder()
                    .id((int) array[0])
                    .firstName((String) array[1])
                    .lastName((String) array[2])
                    .petName((String) array[3])
                    .build();
        }
    }
}
