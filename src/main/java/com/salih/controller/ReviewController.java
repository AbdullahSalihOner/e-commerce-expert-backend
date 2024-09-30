package com.salih.controller;

import com.salih.constant.ApiEndpoints;
import com.salih.dto.review.ReviewRequestDto;
import com.salih.dto.review.ReviewResponseDto;
import com.salih.dto.review.ReviewResponseWithBreadcrumbDto;
import com.salih.model.BreadcrumbItem;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.BreadcrumbService;
import com.salih.service.review.IReviewService;
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
@RequestMapping(ApiEndpoints.REVIEW_BASE)
@RequiredArgsConstructor
@Tag(name = "Review Controller", description = "APIs for managing reviews")
public class ReviewController {

    private final IReviewService reviewService;
    private final BreadcrumbService breadcrumbService;

    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
            .build();

    @Operation(summary = "Get all reviews", description = "Returns a list of all reviews.")
    @GetMapping(ApiEndpoints.REVIEW_GET_ALL)
    public ResponseEntity<ReviewResponseWithBreadcrumbDto> getAllReviews() {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        List<BreadcrumbItem> breadcrumbs = breadcrumbService.generateBreadcrumb("/reviews");

        DataResult<List<ReviewResponseDto>> result = reviewService.getAllReviews();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(new ReviewResponseWithBreadcrumbDto(result.getData(), breadcrumbs));
    }

    @Operation(summary = "Get review by ID", description = "Returns a single review based on the provided ID.")
    @GetMapping(ApiEndpoints.REVIEW_GET_BY_PRODUCT_ID)
    public ResponseEntity<ReviewResponseWithBreadcrumbDto> getReviewById(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }

        List<BreadcrumbItem> breadcrumbs = breadcrumbService.generateBreadcrumb("/reviews/" + id);

        DataResult<ReviewResponseDto> result = reviewService.getReviewById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(new ReviewResponseWithBreadcrumbDto(List.of(result.getData()), breadcrumbs));
    }

    @Operation(summary = "Add new review", description = "Creates a new review for a product.")
    @PostMapping(ApiEndpoints.REVIEW_ADD)
    public ResponseEntity<Result> addReview(@RequestBody @Valid ReviewRequestDto reviewRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = reviewService.addReview(reviewRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Update review", description = "Updates an existing review based on the provided ID.")
    @PutMapping(ApiEndpoints.REVIEW_UPDATE)
    public ResponseEntity<Result> updateReview(@PathVariable Long id, @RequestBody @Valid ReviewRequestDto reviewRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = reviewService.updateReview(id, reviewRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }

    @Operation(summary = "Delete review", description = "Deletes an existing review based on the provided ID.")
    @DeleteMapping(ApiEndpoints.REVIEW_DELETE)
    public ResponseEntity<Result> deleteReview(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = reviewService.deleteReview(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }
}
