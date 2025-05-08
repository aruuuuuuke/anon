package com.example.demo.controllers;

import com.example.demo.models.Reviews;
import com.example.demo.services.ReviewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewsController {

    private final ReviewsService reviewsService;

    @GetMapping
    public List<Reviews> getAllReviews() {
        return reviewsService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable Long id) {
        return reviewsService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reviews createReview(@RequestBody Reviews review) {
        return reviewsService.save(review);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reviews> updateReview(@PathVariable Long id, @RequestBody Reviews review) {
        try {
            return ResponseEntity.ok(reviewsService.update(id, review));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
