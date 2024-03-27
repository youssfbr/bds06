package com.github.youssfbr.movieflix.controllers;

import com.github.youssfbr.movieflix.dtos.MovieDTO;
import com.github.youssfbr.movieflix.dtos.ReviewDTO;
import com.github.youssfbr.movieflix.services.MovieService;
import com.github.youssfbr.movieflix.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final ReviewService reviewService;

    public MovieController(MovieService movieService , ReviewService reviewService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @GetMapping
    public ResponseEntity <Page<MovieDTO>> findAllPaged(
            @RequestParam(value = "genreId", defaultValue = "0") Long genreId, Pageable pageable) {
        return ResponseEntity.ok().body(movieService.findAllPaged(genreId, pageable));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> reviewsForCurrentUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(reviewService.reviewsForCurrentUser(id));
    }

}
