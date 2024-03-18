package com.github.youssfbr.movieflix.controllers;

import com.github.youssfbr.movieflix.dtos.GenreDTO;
import com.github.youssfbr.movieflix.services.GenreService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<GenreDTO>> findAll(Pageable pageable) {
        final List<GenreDTO> all = genreService.findAll();
        return ResponseEntity.ok(all);
    }

}
