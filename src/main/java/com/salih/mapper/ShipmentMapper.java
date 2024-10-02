package com.salih.mapper;

import com.salih.dto.shipment.ShipmentRequestDto;
import com.salih.dto.shipment.ShipmentResponseDto;
import com.salih.model.Shipment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipmentMapper {
    ShipmentResponseDto toDto(Shipment shipment);
    Shipment toEntity(ShipmentRequestDto shipmentRequestDto);
}