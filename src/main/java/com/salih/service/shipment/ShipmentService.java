package com.salih.service.shipment;

import com.salih.dto.shipment.ShipmentRequestDto;
import com.salih.dto.shipment.ShipmentResponseDto;
import com.salih.exception.ResourceNotFoundException;
import com.salih.mapper.ShipmentMapper;
import com.salih.model.Shipment;
import com.salih.repository.ShipmentRepository;
import com.salih.result.DataResult;
import com.salih.result.Result;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShipmentService implements IShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;
    private static final Logger logger = LoggerFactory.getLogger(ShipmentService.class);

    @Override
    public DataResult<List<ShipmentResponseDto>> getAllShipments() {
        List<Shipment> shipments = shipmentRepository.findAll();
        if (shipments.isEmpty()) {
            logger.warn("No shipments found");
            throw new ResourceNotFoundException("No shipments found");
        }

        List<ShipmentResponseDto> shipmentDtos = shipments.stream()
                .map(shipmentMapper::toDto)
                .collect(Collectors.toList());

        logger.info("Shipments listed successfully");
        return new DataResult<>(shipmentDtos, Result.showMessage(Result.SUCCESS, "Shipments listed successfully"));
    }

    @Override
    public DataResult<ShipmentResponseDto> getShipmentById(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Shipment not found with ID: {}", id);
                    return new ResourceNotFoundException("Shipment not found with ID: " + id);
                });

        ShipmentResponseDto shipmentDto = shipmentMapper.toDto(shipment);
        logger.info("Shipment found with ID: {}", id);
        return new DataResult<>(shipmentDto, Result.showMessage(Result.SUCCESS, "Shipment found"));
    }

    @Override
    public Result addShipment(ShipmentRequestDto shipmentRequestDto) {
        Shipment shipment = shipmentMapper.toEntity(shipmentRequestDto);
        shipmentRepository.save(shipment);
        logger.info("Shipment added successfully with ID: {}", shipment.getId());
        return Result.showMessage(Result.SUCCESS, "Shipment added successfully");
    }

    @Override
    public Result updateShipment(Long id, ShipmentRequestDto shipmentRequestDto) {
        Shipment existingShipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        existingShipment.setTrackingNumber(shipmentRequestDto.getTrackingNumber());
        existingShipment.setEstimatedDeliveryDate(shipmentRequestDto.getEstimatedDeliveryDate());

        shipmentRepository.save(existingShipment);
        logger.info("Shipment updated successfully with ID: {}", id);
        return Result.SUCCESS;
    }

    @Override
    public Result updateShipmentStatus(Long id, String status) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        shipment.setShipmentStatus(Shipment.ShipmentStatus.valueOf(status.toUpperCase()));

        shipmentRepository.save(shipment);
        logger.info("Shipment status updated successfully with ID: {}", id);
        return Result.SUCCESS;
    }

    @Override
    public Result deleteShipment(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with ID: " + id));

        shipmentRepository.delete(shipment);
        logger.info("Shipment deleted successfully with ID: {}", id);
        return Result.showMessage(Result.SUCCESS, "Shipment deleted successfully");
    }
}