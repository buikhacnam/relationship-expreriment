package com.buinam.relationshipexperiment.controller;


import com.buinam.relationshipexperiment.model.Address;
import com.buinam.relationshipexperiment.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressRepository addressRepository;

    @PostMapping("/save")
    public Address saveAddress(@RequestBody Address address) {
        return addressRepository.save(address);
    }

    @GetMapping("/all")
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }
}
