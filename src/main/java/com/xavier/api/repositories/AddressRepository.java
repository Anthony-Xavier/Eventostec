package com.xavier.api.repositories;

import com.xavier.api.domain.address.Address;
import com.xavier.api.domain.cupon.Cupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
