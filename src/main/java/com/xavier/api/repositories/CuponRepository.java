package com.xavier.api.repositories;

import com.xavier.api.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CuponRepository extends JpaRepository<Coupon, UUID> {
}
