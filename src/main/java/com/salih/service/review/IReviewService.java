package com.salih.service.review;

import com.salih.dto.review.ReviewRequestDto;
import com.salih.dto.review.ReviewResponseDto;
import com.salih.result.DataResult;
import com.salih.result.Result;

import java.util.List;

public interface IReviewService {
    DataResult<List<ReviewResponseDto>> getAllReviews();
    DataResult<ReviewResponseDto> getReviewById(Long id);
    Result addReview(ReviewRequestDto reviewRequestDto);
    Result updateReview(Long id, ReviewRequestDto reviewRequestDto);
    Result deleteReview(Long id);
}
