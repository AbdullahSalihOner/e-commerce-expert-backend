package com.salih.controller;

import com.salih.constant.ApiEndpoints;
import com.salih.dto.review.ReviewRequestDto;
import com.salih.dto.review.ReviewResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.review.IReviewService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
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
public class ReviewController {

    private final IReviewService reviewService;

    // Rate limiting bucket: 100 requests per minute
    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
            .build();

    @GetMapping(ApiEndpoints.REVIEW_GET_ALL)
    public ResponseEntity<DataResult<List<ReviewResponseDto>>> getAllReviews() {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<List<ReviewResponseDto>> result = reviewService.getAllReviews();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping(ApiEndpoints.REVIEW_GET_BY_PRODUCT_ID)
    public ResponseEntity<DataResult<ReviewResponseDto>> getReviewById(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        DataResult<ReviewResponseDto> result = reviewService.getReviewById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping(ApiEndpoints.REVIEW_ADD)
    public ResponseEntity<Result> addReview(@RequestBody @Valid ReviewRequestDto reviewRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = reviewService.addReview(reviewRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }

    @PutMapping(ApiEndpoints.REVIEW_UPDATE)
    public ResponseEntity<Result> updateReview(@PathVariable Long id, @RequestBody @Valid ReviewRequestDto reviewRequestDto) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = reviewService.updateReview(id, reviewRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }

    @DeleteMapping(ApiEndpoints.REVIEW_DELETE)
    public ResponseEntity<Result> deleteReview(@PathVariable Long id) {
        if (!bucket.tryConsume(1)) {
            return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        Result result = reviewService.deleteReview(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST).body(result);
    }
}
