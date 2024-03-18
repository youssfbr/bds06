package com.github.youssfbr.movieflix.repositories;

import com.github.youssfbr.movieflix.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
