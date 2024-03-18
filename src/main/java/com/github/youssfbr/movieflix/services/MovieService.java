package com.github.youssfbr.movieflix.services;

import com.github.youssfbr.movieflix.dtos.MovieDTO;
import com.github.youssfbr.movieflix.repositories.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public MovieDTO findById(Long id) {
        return movieRepository.findById(id)
                .map(MovieDTO::new)
                .orElseThrow(() -> new RuntimeException("Id not found " + id));
    }
}
