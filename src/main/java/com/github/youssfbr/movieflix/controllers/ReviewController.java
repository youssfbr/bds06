package com.github.youssfbr.movieflix.controllers;

import com.github.youssfbr.movieflix.dtos.ReviewDTO;
import com.github.youssfbr.movieflix.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) { this.service = service; }

    @PostMapping
    @PreAuthorize("hasAnyRole('MEMBER')")
    public ResponseEntity<ReviewDTO> insert(@Valid @RequestBody ReviewDTO dto) {

        final ReviewDTO reviewCreated = service.insert(dto);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(reviewCreated.getId()).toUri();

        return ResponseEntity.created(uri).body(reviewCreated);
    }
}

