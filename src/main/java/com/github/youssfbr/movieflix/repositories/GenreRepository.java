package com.github.youssfbr.movieflix.repositories;

import com.github.youssfbr.movieflix.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
