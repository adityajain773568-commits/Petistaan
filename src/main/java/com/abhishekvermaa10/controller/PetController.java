package com.abhishekvermaa10.controller;

import com.abhishekvermaa10.dto.AverageAgeDTO;
import com.abhishekvermaa10.dto.PetDTO;
import com.abhishekvermaa10.exception.PetNotFoundException;
import com.abhishekvermaa10.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/pets")
@RestController
public class PetController {

    private final PetService petService;

    //    fetch pet details.
    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> findPet(@PathVariable Integer petId) throws PetNotFoundException {
        PetDTO pet = petService.findPet(petId);
        return ResponseEntity.ok(pet);
    }

    //    find average age of pet.
    @GetMapping("/average-age")
    public ResponseEntity<AverageAgeDTO> findAverageAge() {
        AverageAgeDTO averageAgeOfPet = petService.findAverageAgeOfPet();
        return ResponseEntity.ok(averageAgeOfPet);
    }
}
