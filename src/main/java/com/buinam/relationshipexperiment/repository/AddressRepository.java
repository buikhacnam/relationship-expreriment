package com.buinam.relationshipexperiment.repository;

import com.buinam.relationshipexperiment.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
