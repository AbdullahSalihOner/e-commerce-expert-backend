package com.salih.service.review;

import com.salih.dto.review.ReviewRequestDto;
import com.salih.dto.review.ReviewResponseDto;
import com.salih.exception.ResourceNotFoundException;
import com.salih.mapper.ReviewMapper;
import com.salih.model.Product;
import com.salih.model.Review;
import com.salih.model.User;
import com.salih.repository.ProductRepository;
import com.salih.repository.ReviewRepository;
import com.salih.repository.UserRepository;
import com.salih.result.DataResult;
import com.salih.result.Result;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;
    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Override
    public DataResult<List<ReviewResponseDto>> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        if (reviews.isEmpty()) {
            logger.warn("No reviews found");
            throw new ResourceNotFoundException("No reviews found");
        }

        List<ReviewResponseDto> reviewDtos = reviews.stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());

        logger.info("Reviews listed successfully");
        return new DataResult<>(reviewDtos, Result.showMessage(Result.SUCCESS, "Reviews listed successfully"));
    }

    @Cacheable(value = "reviews", key = "#id")
    @Override
    public DataResult<ReviewResponseDto> getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));

        ReviewResponseDto reviewDto = reviewMapper.toDto(review);
        logger.info("Review found with ID: {}", id);
        return new DataResult<>(reviewDto, Result.showMessage(Result.SUCCESS, "Review found"));
    }

    @Override
    public Result addReview(ReviewRequestDto reviewRequestDto) {
        Product product = productRepository.findById(reviewRequestDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + reviewRequestDto.getProductId()));

        User buyer = userRepository.findById(reviewRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with ID: " + reviewRequestDto.getUserId()));

        Review review = reviewMapper.toEntity(reviewRequestDto);
        review.setProduct(product);
        review.setUser(buyer);

        reviewRepository.save(review);
        logger.info("Review added successfully with ID: {}", review.getId());
        return Result.showMessage(Result.SUCCESS, "Review added successfully");
    }

    @Override
    public Result updateReview(Long id, ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));

        Product product = productRepository.findById(reviewRequestDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + reviewRequestDto.getProductId()));

        User buyer = userRepository.findById(reviewRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found with ID: " + reviewRequestDto.getUserId()));

        review.setRating(reviewRequestDto.getRating());
        review.setComment(reviewRequestDto.getComment());
        review.setProduct(product);
        review.setUser(buyer);

        reviewRepository.save(review);
        logger.info("Review updated successfully with ID: {}", id);
        return Result.showMessage(Result.SUCCESS, "Review updated successfully");
    }

    @CacheEvict(value = "reviews", key = "#id")
    @Override
    public Result deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + id));

        reviewRepository.delete(review);
        logger.info("Review deleted successfully with ID: {}", id);
        return Result.showMessage(Result.SUCCESS, "Review deleted successfully");
    }
}

