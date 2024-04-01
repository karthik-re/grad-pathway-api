package org.example.gradpathway.service;

import org.example.gradpathway.DTO.ReviewDTO;
import org.example.gradpathway.entity.Company;
import org.example.gradpathway.entity.Reviews;
import org.example.gradpathway.entity.User;
import org.example.gradpathway.repository.CompanyRepository;
import org.example.gradpathway.repository.ReviewsRepository;
import org.example.gradpathway.repository.UserRepository;
import org.example.gradpathway.util.AuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        Company company = companyRepository.findById(reviewDTO.getCompanyId()).orElse(null);

        Optional<User> user = authenticationDetails.getUser();

        if(company == null || user.isEmpty()){
            throw new IllegalArgumentException("Unknown company or user");
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
        Reviews review = reviewsRepository.findById(id).orElse(null);
        if(review != null){
            review.setTitle(reviewDTO.getTitle());
            review.setDescription(reviewDTO.getDescription());
            review.setRating(reviewDTO.getRating());
            reviewsRepository.save(review);
        }
    }

    @Override
    public void deleteReview(int id) {
        reviewsRepository.deleteById(id);
    }

    @Override
    public List<Reviews> getAllReviews() {
        return reviewsRepository.findAll();
    }

    @Override
    public Reviews getReviewById(int id) {
        return reviewsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reviews> getReviewsByCompanyId(int companyId) {
        return reviewsRepository.findAllByCompanyId(companyId);
    }

    @Override
    public List<Reviews> getReviewsByCompanyIdOrderBy(int companyId, String order) {
        if(order.equals("ASC")){
            return reviewsRepository.findAllByCompanyIdOrderByWorst(companyId);
        }
        return reviewsRepository.findAllByCompanyIdOrderByBest(companyId);
    }
}
