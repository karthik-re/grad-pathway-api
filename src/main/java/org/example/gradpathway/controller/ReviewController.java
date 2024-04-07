package org.example.gradpathway.controller;

import jakarta.validation.Valid;
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
    public ResponseEntity<String> addReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        try {
            reviewService.addReview(reviewDTO);
            return ResponseEntity.ok("Review added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateReview(@Valid @RequestBody ReviewDTO reviewDTO, @PathVariable int id) {
        try {
            reviewService.updateReview(reviewDTO, id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Review updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable int id) {
        try{
            reviewService.deleteReview(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Review deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable int id) {
        try {
            reviewService.getReviewById(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<?> getReviewsByCompanyId(@PathVariable int companyId) {
        try{
            return ResponseEntity.ok(reviewService.getReviewsByCompanyId(companyId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/company/{companyId}/order/{order}")
    public ResponseEntity<?> getReviewsByCompanyIdOrderBy(@PathVariable int companyId, @PathVariable String order) {
        try{
            return ResponseEntity.ok(reviewService.getReviewsByCompanyIdOrderBy(companyId, order));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
