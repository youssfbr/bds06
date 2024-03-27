package com.github.youssfbr.movieflix.services;

import com.github.youssfbr.movieflix.dtos.GenreDTO;
import com.github.youssfbr.movieflix.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public List<GenreDTO> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(GenreDTO::new)
                .collect(Collectors.toList());
    }
}
