package com.abhishekvermaa10.controller;

import com.abhishekvermaa10.dto.OwnerDTO;
import com.abhishekvermaa10.dto.OwnerIdDTO;
import com.abhishekvermaa10.dto.OwnerPetInfoDTO;
import com.abhishekvermaa10.dto.UpdatePetDTO;
import com.abhishekvermaa10.exception.OwnerNotFoundException;
import com.abhishekvermaa10.service.OwnerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RequestMapping("/owners")
@RestController
public class OwnerController {

    private final OwnerService ownerService;

    // add new owner
    @PostMapping
    public ResponseEntity<OwnerIdDTO> saveOwner(@Valid @RequestBody OwnerDTO ownerDTO) {
        System.out.println(ownerDTO);
        OwnerIdDTO ownerIdDTO = ownerService.saveOwner(ownerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ownerIdDTO);
    }

    // fetch owner details
    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerDTO> findOwner(@PathVariable @Min(value = 1, message = "{owner.id.positive}") Integer ownerId) throws OwnerNotFoundException {
        OwnerDTO ownerDTO = ownerService.findOwner(ownerId);
        return ResponseEntity.ok(ownerDTO);
    }

    // update pet details of owner.
    @PatchMapping
    public ResponseEntity<Void> updatePetDetails(@RequestParam @Min(value = 1, message = "{owner.id.positive}") int ownerId, @Valid @RequestBody UpdatePetDTO updatedPetDTO) throws OwnerNotFoundException {
        ownerService.updatePetDetails(ownerId, updatedPetDTO.getName());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // delete owner details
    @DeleteMapping("/{ownerId}")
    public ResponseEntity<Void> deleteOwner(@PathVariable @Min(value = 1, message = "{owner.id.positive}") Integer ownerId) throws OwnerNotFoundException {
        ownerService.deleteOwner(ownerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // fetch all owners.
    @GetMapping()
    public ResponseEntity<List<OwnerDTO>> allOwners() {
        List<OwnerDTO> ownerDTOList = ownerService.findAllOwners();
        return ResponseEntity.ok(ownerDTOList);
    }

    // find specific details using pagination.
    @GetMapping("/details/page")
    public ResponseEntity<Page<OwnerPetInfoDTO>> getDetailsInPage(Pageable pageable) {
        Page<OwnerPetInfoDTO> page = ownerService.findIdAndFirstNameAndLastNameAndPetNameOfPaginatedOwners(pageable);
        return ResponseEntity.ok(page);
    }

}
