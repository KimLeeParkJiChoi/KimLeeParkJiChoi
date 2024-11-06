package com.sparta.spangeats.domain.address.repository;

import com.sparta.spangeats.domain.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByMemberId(Long id);

    int countByMemberId(Long id);
}
