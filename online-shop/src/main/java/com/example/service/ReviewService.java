package com.example.service;

import com.example.model.dto.CreateReviewDTO;
import com.example.model.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getReviews(Long productId);
    ReviewDTO addReview(Long productId, CreateReviewDTO dto, String userNameOpt);
}
