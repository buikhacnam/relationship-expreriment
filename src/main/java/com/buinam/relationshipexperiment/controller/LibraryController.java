package com.buinam.relationshipexperiment.controller;


import com.buinam.relationshipexperiment.model.Address;
import com.buinam.relationshipexperiment.model.Library;
import com.buinam.relationshipexperiment.repository.AddressRepository;
import com.buinam.relationshipexperiment.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryRepository libraryRepository;
    private final AddressRepository addressRepository;

    @PostMapping("/save")
    public Library saveLibrary(@RequestBody Library library) {
        return libraryRepository.save(library);
    }

    @PutMapping("/assign-address/{libraryId}/{addressId}")
    public Library assignAddress(@PathVariable Long libraryId, @PathVariable Long addressId) {
        Library library = libraryRepository.findById(libraryId).orElseThrow(() -> new IllegalArgumentException("Library not found"));
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new IllegalArgumentException("Address not found"));
        library.setAddress(address);
        return libraryRepository.save(library);
    }

    @GetMapping("/all")
    public List<Library> getAllLibraries() {
        try {
            return libraryRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/detail/{libraryId}")
    public Library getLibraryDetail(@PathVariable Long libraryId) {
        try {
            return libraryRepository.findById(libraryId).orElseThrow(() -> new IllegalArgumentException("Library not found"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
