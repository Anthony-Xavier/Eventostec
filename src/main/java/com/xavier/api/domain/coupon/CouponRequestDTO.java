package com.xavier.api.domain.coupon;

public record CouponRequestDTO(String code, Integer discount, Long valid) {
}
