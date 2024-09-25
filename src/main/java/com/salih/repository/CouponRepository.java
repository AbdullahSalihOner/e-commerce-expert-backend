package com.salih.repository;

import com.salih.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Optional<Coupon> findByCode(String code);
    List<Coupon> findByProductId(Long productId);
}
