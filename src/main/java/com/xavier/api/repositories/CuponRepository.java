package com.xavier.api.repositories;

import com.xavier.api.domain.cupon.Cupon;
import com.xavier.api.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CuponRepository extends JpaRepository<Cupon, UUID> {
}
