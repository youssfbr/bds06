package com.github.youssfbr.movieflix.controllers;

import com.github.youssfbr.movieflix.dtos.MovieDTO;
import com.github.youssfbr.movieflix.services.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<MovieDTO>> findByGenre(
            @RequestParam(value = "genreId", defaultValue = "0") Long genreId,
            Pageable pageable)
    {
        final Page<MovieDTO> movieDTOS = movieService.finByGenre(genreId , pageable);
        return ResponseEntity.ok().body(movieDTOS);
    }
}
