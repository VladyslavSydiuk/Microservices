package com.example.service.impl;

import com.example.model.Product;
import com.example.model.Review;
import com.example.model.dto.CreateReviewDTO;
import com.example.model.dto.ReviewDTO;
import com.example.repository.ProductRepository;
import com.example.repository.ReviewRepository;
import com.example.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ReviewDTO> getReviews(Long productId) {
        return reviewRepository.findByProductIdOrderByCreatedAtDesc(productId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO addReview(Long productId, CreateReviewDTO dto, String userNameOpt) {
        // validations
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Body is required");
        }
        if (dto.getRating() == null || dto.getRating() < 1 || dto.getRating() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "rating must be between 1 and 5");
        }
        if (dto.getComment() == null || dto.getComment().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment must not be blank");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        Review review = Review.builder()
                .product(product)
                .userName(userNameOpt)
                .rating(dto.getRating())
                .comment(dto.getComment().trim())
                .build();

        Review saved = reviewRepository.save(review);
        return toDTO(saved);
    }

    private ReviewDTO toDTO(Review r) {
        return ReviewDTO.builder()
                .id(r.getId())
                .productId(r.getProduct() != null ? r.getProduct().getId() : null)
                .userName(r.getUserName())
                .rating(r.getRating())
                .comment(r.getComment())
                .createdAt(r.getCreatedAt())
                .build();
    }
}
