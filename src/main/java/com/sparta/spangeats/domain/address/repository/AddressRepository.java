package com.sparta.spangeats.domain.address.repository;

import com.sparta.spangeats.domain.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
