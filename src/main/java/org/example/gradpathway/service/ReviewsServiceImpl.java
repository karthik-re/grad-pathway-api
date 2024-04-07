package org.example.gradpathway.service;

import org.example.gradpathway.DTO.ReviewDTO;
import org.example.gradpathway.DTO.ReviewResDTO;
import org.example.gradpathway.entity.Company;
import org.example.gradpathway.entity.Reviews;
import org.example.gradpathway.entity.User;
import org.example.gradpathway.repository.CompanyRepository;
import org.example.gradpathway.repository.ReviewsRepository;
import org.example.gradpathway.util.AuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewsServiceImpl implements ReviewsService{

    private final ReviewsRepository reviewsRepository;

    private final CompanyRepository companyRepository;

    private final AuthenticationDetails authenticationDetails;

    @Autowired
    public ReviewsServiceImpl(ReviewsRepository reviewsRepository,
                              CompanyRepository companyRepository,AuthenticationDetails authenticationDetails) {
        this.reviewsRepository = reviewsRepository;
        this.companyRepository = companyRepository;
        this.authenticationDetails = authenticationDetails;

    }
    @Override
    public void addReview(ReviewDTO reviewDTO) {
        Company company = companyRepository.findById(reviewDTO.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        Optional<User> user = authenticationDetails.getUser();

        if(user.isEmpty()){
            throw new IllegalArgumentException("Unauthorized");
        }
            Reviews review = Reviews.builder()
                    .id(0)
                    .title(reviewDTO.getTitle())
                    .description(reviewDTO.getDescription())
                    .rating(reviewDTO.getRating())
                    .company(company)
                    .user(user.get())
                    .postedAt(new Date())
                    .build();

            reviewsRepository.save(review);
    }

    @Override
    public void updateReview(ReviewDTO reviewDTO, int id) {
        Reviews review = reviewsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Review not found"));
        if(review != null){
            review.setTitle(reviewDTO.getTitle());
            review.setDescription(reviewDTO.getDescription());
            review.setRating(reviewDTO.getRating());
            reviewsRepository.save(review);
        }
    }

    @Override
    public void deleteReview(int id) {
        reviewsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Review not found"));
        reviewsRepository.deleteById(id);
    }

    @Override
    public List<ReviewResDTO> getAllReviews() {
        return reviewsRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public ReviewResDTO getReviewById(int id) {
        Reviews review = reviewsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Review not found"));
        return convertToDTO(review);
    }

    @Override
    public List<ReviewResDTO> getReviewsByCompanyId(int companyId) {
        if(companyRepository.findById(companyId).isEmpty()){
            throw new IllegalArgumentException("Company not found");
        }
        return reviewsRepository.findAllByCompanyId(companyId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<ReviewResDTO> getReviewsByCompanyIdOrderBy(int companyId, String order) {
        if(companyRepository.findById(companyId).isEmpty()){
            throw new IllegalArgumentException("Company not found");
        }
        if(order.equals("ASC")){
            return reviewsRepository.findAllByCompanyIdOrderByWorst(companyId)
                    .stream()
                    .map(this::convertToDTO)
                    .toList();
        }
        return reviewsRepository.findAllByCompanyIdOrderByBest(companyId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private ReviewResDTO convertToDTO(Reviews reviews){
        return ReviewResDTO.builder()
                .id(reviews.getId())
                .title(reviews.getTitle())
                .description(reviews.getDescription())
                .rating(reviews.getRating())
                .companyName(reviews.getCompany().getName())
                .userFirstName(reviews.getUser().getFirstName())
                .userLastName(reviews.getUser().getLastName())
                .postedAt(reviews.getPostedAt())
                .build();
    }
}
