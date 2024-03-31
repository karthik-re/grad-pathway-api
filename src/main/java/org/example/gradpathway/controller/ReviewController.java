package org.example.gradpathway.controller;

import org.example.gradpathway.DTO.ReviewDTO;
import org.example.gradpathway.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewsService reviewService;

    @Autowired
    public ReviewController(ReviewsService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestBody ReviewDTO reviewDTO) {
        reviewService.addReview(reviewDTO);
        return ResponseEntity.ok("Review added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateReview(@RequestBody ReviewDTO reviewDTO, @PathVariable int id) {
        reviewService.updateReview(reviewDTO, id);
        return ResponseEntity.ok("Review updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable int id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getReviewsByCompanyId(@PathVariable int companyId) {
        return ResponseEntity.ok(reviewService.getReviewsByCompanyId(companyId));
    }

    @GetMapping("/company/{companyId}/order/{order}")
    public ResponseEntity<?> getReviewsByCompanyIdOrderBy(@PathVariable int companyId, @PathVariable String order) {
        return ResponseEntity.ok(reviewService.getReviewsByCompanyIdOrderBy(companyId, order));
    }
}
