package com.github.youssfbr.movieflix.services;

import com.github.youssfbr.movieflix.dtos.MovieDTO;
import com.github.youssfbr.movieflix.entities.Genre;
import com.github.youssfbr.movieflix.entities.Movie;
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
    private final MovieRepository repository;
    private final GenreRepository genreRepository;

    public MovieService(MovieRepository repository , GenreRepository genreRepository) {
        this.repository = repository;
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public Page<MovieDTO> findAllPaged(Long genreId, Pageable pageable) {

        final List<Genre> genres = (genreId == 0) ? null : List.of(genreRepository.getOne(genreId));
        final Page<Movie> page = repository.find(genres, pageable);
        repository.findProductsWithCategories(page.getContent());

        return page.map(MovieDTO::new);
    }

    @Transactional(readOnly = true)
    public MovieDTO findById(Long id) {
        return repository.findById(id)
                .map(MovieDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
    }
}