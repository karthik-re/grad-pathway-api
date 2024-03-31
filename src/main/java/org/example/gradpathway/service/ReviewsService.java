package org.example.gradpathway.service;

import org.example.gradpathway.DTO.ReviewDTO;
import org.example.gradpathway.entity.Reviews;

import java.util.List;

public interface ReviewsService {

    void addReview(ReviewDTO reviewDTO);

    void updateReview(ReviewDTO reviewDTO, int id);

    void deleteReview(int id);

    List<Reviews> getAllReviews();

    Reviews getReviewById(int id);

    List<Reviews> getReviewsByCompanyId(int companyId);

    List<Reviews> getReviewsByCompanyIdOrderBy(int companyId, String order);
}
