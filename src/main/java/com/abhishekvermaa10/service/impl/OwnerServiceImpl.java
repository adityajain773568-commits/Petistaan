package com.abhishekvermaa10.service.impl;

import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.OwnerIdDTO;
import com.abhishekvermaa10.dto.OwnerPetInfoDTO;
import com.abhishekvermaa10.entity.Owner;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.repository.OwnerRepository;
import com.abhishekvermaa10.service.OwnerService;
import com.abhishekvermaa10.util.OwnerMapper;
import com.abhishekvermaa10.util.OwnerPetInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author abhishekvermaa10
 */
@RequiredArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
    private final OwnerPetInfoMapper ownerPetInfoMapper;
    @Value("${owner.not.found}")
    private String ownerNotFound;

    @Override
    public OwnerIdDTO saveOwner(OwnerDTO ownerDTO) {
        Owner owner = ownerMapper.ownerDTOToOwner(ownerDTO);
        owner = ownerRepository.save(owner);
        return OwnerIdDTO.builder()
                .ownerId(owner.getId())
                .build();
    }

    @Override
    public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
        return ownerRepository.findById(ownerId)
                .map(ownerMapper::ownerToOwnerDTO)
                .orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
    }

    @Override
    public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException(String.format(ownerNotFound, ownerId)));
        owner.getPet().setName(petName);
        ownerRepository.save(owner);
    }

    @Override
    public void deleteOwner(int ownerId) throws OwnerNotFoundException {
        boolean ownerExists = ownerRepository.existsById(ownerId);
        if (!ownerExists) {
            throw new OwnerNotFoundException(String.format(ownerNotFound, ownerId));
        } else {
            ownerRepository.deleteById(ownerId);
        }
    }

    @Override
    public List<OwnerDTO> findAllOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(ownerMapper::ownerToOwnerDTO)
                .toList();
    }

    @Override
    public Page<OwnerPetInfoDTO> findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(Pageable pageable) {
        Page<Object[]> page = ownerRepository.findIdAndFirstNameAndLastNameAndPetName(pageable);
        List<OwnerPetInfoDTO> list = page.stream().map(ownerPetInfoMapper::mapObjectArrayToOwnerPetInfoDTO).toList();
        return new PageImpl<>(list, pageable, page.getTotalElements());
    }

}
