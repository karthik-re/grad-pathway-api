package org.example.gradpathway.service;

import org.example.gradpathway.DTO.ReviewDTO;
import org.example.gradpathway.DTO.ReviewResDTO;

import java.util.List;

public interface ReviewsService {

    void addReview(ReviewDTO reviewDTO);

    void updateReview(ReviewDTO reviewDTO, int id);

    void deleteReview(int id);

    List<ReviewResDTO> getAllReviews();

    ReviewResDTO getReviewById(int id);

    List<ReviewResDTO> getReviewsByCompanyId(int companyId);

    List<ReviewResDTO> getReviewsByCompanyIdOrderBy(int companyId, String order);
}
