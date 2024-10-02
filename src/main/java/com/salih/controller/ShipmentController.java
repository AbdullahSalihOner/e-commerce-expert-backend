package com.salih.controller;

import com.salih.constant.ApiEndpoints;
import com.salih.dto.shipment.ShipmentRequestDto;
import com.salih.dto.shipment.ShipmentResponseDto;
import com.salih.model.BreadcrumbItem;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.BreadcrumbService;
import com.salih.service.shipment.IShipmentService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping(ApiEndpoints.SHIPMENT_BASE)
@RequiredArgsConstructor
@Tag(name = "Shipment Controller", description = "APIs for managing shipments")
public class ShipmentController {

    private final IShipmentService shipmentService;
    private final BreadcrumbService breadcrumbService;

    // Rate limiting: 100 requests per minute
    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
            .build();

    @Operation(summary = "Get all shipments", description = "Returns a list of all shipments.")
    @GetMapping(ApiEndpoints.SHIPMENT_GET_ALL)
    public ResponseEntity<DataResult<List<ShipmentResponseDto>>> getAllShipments(@RequestHeader("referer") String referer) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<List<ShipmentResponseDto>> result = shipmentService.getAllShipments();

        // Breadcrumb ekleme
        List<BreadcrumbItem> breadcrumbs = breadcrumbService.generateBreadcrumb(referer);

        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .header("Breadcrumb", breadcrumbs.toString()) // Breadcrumb'ları header olarak da dönebiliriz
                .body(result);
    }

    @Operation(summary = "Get shipment by ID", description = "Returns a shipment by the provided ID.")
    @GetMapping(ApiEndpoints.SHIPMENT_GET_BY_ID)
    public ResponseEntity<DataResult<ShipmentResponseDto>> getShipmentById(@PathVariable Long id, @RequestHeader("referer") String referer) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<ShipmentResponseDto> result = shipmentService.getShipmentById(id);

        // Breadcrumb ekleme
        List<BreadcrumbItem> breadcrumbs = breadcrumbService.generateBreadcrumb(referer + "/" + id);

        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .header("Breadcrumb", breadcrumbs.toString())
                .body(result);
    }

    @Operation(summary = "Create new shipment", description = "Creates a new shipment for an order.")
    @PostMapping(ApiEndpoints.SHIPMENT_CREATE)
    public ResponseEntity<Result> addShipment(@RequestBody @Valid ShipmentRequestDto shipmentRequestDto, @RequestHeader("referer") String referer) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = shipmentService.addShipment(shipmentRequestDto);

        // Breadcrumb ekleme
        List<BreadcrumbItem> breadcrumbs = breadcrumbService.generateBreadcrumb(referer);

        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
                .header("Breadcrumb", breadcrumbs.toString())
                .body(result);
    }

    @Operation(summary = "Update shipment", description = "Updates an existing shipment by ID.")
    @PutMapping(ApiEndpoints.SHIPMENT_UPDATE)
    public ResponseEntity<Result> updateShipment(@PathVariable Long id, @RequestBody @Valid ShipmentRequestDto shipmentRequestDto, @RequestHeader("referer") String referer) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = shipmentService.updateShipment(id, shipmentRequestDto);

        // Breadcrumb ekleme
        List<BreadcrumbItem> breadcrumbs = breadcrumbService.generateBreadcrumb(referer + "/update/" + id);

        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .header("Breadcrumb", breadcrumbs.toString())
                .body(result);
    }

    @Operation(summary = "Update shipment status", description = "Updates the status of a shipment.")
    @PutMapping(ApiEndpoints.SHIPMENT_UPDATE_STATUS)
    public ResponseEntity<Result> updateShipmentStatus(@PathVariable Long id, @RequestParam String status, @RequestHeader("referer") String referer) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = shipmentService.updateShipmentStatus(id, status);

        // Breadcrumb ekleme
        List<BreadcrumbItem> breadcrumbs = breadcrumbService.generateBreadcrumb(referer + "/status/" + id);

        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .header("Breadcrumb", breadcrumbs.toString())
                .body(result);
    }

    @Operation(summary = "Delete shipment", description = "Deletes a shipment by ID.")
    @DeleteMapping(ApiEndpoints.SHIPMENT_DELETE)
    public ResponseEntity<Result> deleteShipment(@PathVariable Long id, @RequestHeader("referer") String referer) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = shipmentService.deleteShipment(id);

        // Breadcrumb ekleme
        List<BreadcrumbItem> breadcrumbs = breadcrumbService.generateBreadcrumb(referer + "/delete/" + id);

        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .header("Breadcrumb", breadcrumbs.toString())
                .body(result);
    }
}