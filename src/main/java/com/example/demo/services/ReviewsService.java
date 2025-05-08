package com.example.demo.services;

import com.example.demo.models.Reviews;
import com.example.demo.repositories.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewsService {

    private final ReviewsRepository reviewsRepository;

    public List<Reviews> findAll() {
        return reviewsRepository.findAll();
    }

    public Optional<Reviews> findById(Long id) {
        return reviewsRepository.findById(id);
    }

    public Reviews save(Reviews review) {
        return reviewsRepository.save(review);
    }

    public Reviews update(Long id, Reviews updatedReview) {
        return reviewsRepository.findById(id).map(review -> {
            review.setName(updatedReview.getName());
            review.setText(updatedReview.getText());
            review.setRating(updatedReview.getRating());
            return reviewsRepository.save(review);
        }).orElseThrow(() -> new RuntimeException("Отзыв не найден"));
    }

    public void delete(Long id) {
        reviewsRepository.deleteById(id);
    }
}
