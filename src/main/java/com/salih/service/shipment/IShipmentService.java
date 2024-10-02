package com.salih.service.shipment;


import com.salih.dto.shipment.ShipmentRequestDto;
import com.salih.dto.shipment.ShipmentResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;

import java.util.List;

public interface IShipmentService {
    DataResult<List<ShipmentResponseDto>> getAllShipments();
    DataResult<ShipmentResponseDto> getShipmentById(Long id);
    Result addShipment(ShipmentRequestDto shipmentRequestDto);
    Result updateShipment(Long id, ShipmentRequestDto shipmentRequestDto);
    Result updateShipmentStatus(Long id, String status);  // Kargo durumu güncelleme
    Result deleteShipment(Long id);
}
