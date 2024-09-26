package com.salih.controller;

import com.salih.dto.review.ReviewRequestDto;
import com.salih.dto.review.ReviewResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;
import com.salih.service.review.IReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewService;

    @GetMapping("/all")
    public ResponseEntity<DataResult<List<ReviewResponseDto>>> getAllReviews() {
        DataResult<List<ReviewResponseDto>> result = reviewService.getAllReviews();
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<ReviewResponseDto>> getReviewById(@PathVariable Long id) {
        DataResult<ReviewResponseDto> result = reviewService.getReviewById(id);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(result);
    }

    @PostMapping("/add")
    public ResponseEntity<Result> addReview(@RequestBody @Valid ReviewRequestDto reviewRequestDto) {
        Result result = reviewService.addReview(reviewRequestDto);
        return ResponseEntity.status(result.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(result);
    }
}
