package com.salih.repository;

import com.salih.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findByUserId(Long userId);
    List<Shipment> findByShipmentStatus(Shipment.ShipmentStatus status);
}
