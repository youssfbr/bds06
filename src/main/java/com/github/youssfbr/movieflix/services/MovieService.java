package com.github.youssfbr.movieflix.services;

import com.github.youssfbr.movieflix.dtos.MovieDTO;
import com.github.youssfbr.movieflix.entities.Genre;
import com.github.youssfbr.movieflix.repositories.GenreRepository;
import com.github.youssfbr.movieflix.repositories.MovieRepository;
import com.github.youssfbr.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    public MovieService(MovieRepository movieRepository , GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public MovieDTO findById(Long id) {
        return movieRepository.findById(id)
                .map(MovieDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found with id " + id));
    }

    @Transactional(readOnly = true)
    public Page<MovieDTO> finByGenre(Long genreId, Pageable pageable) {
        final List<Genre> movies = (genreId == 0) ? null : List.of(genreRepository.getOne(genreId));
        return movieRepository.find(movies, genreId, pageable);
    }
}
