package com.github.youssfbr.movieflix.repositories;

import com.github.youssfbr.movieflix.entities.Genre;
import com.github.youssfbr.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT DISTINCT obj FROM Movie obj INNER JOIN obj.genre gen WHERE "
            + "(COALESCE(:genres) IS NULL OR gen IN :genres) "
            + "ORDER BY obj.title")
    Page<Movie> find(List<Genre> genres, Pageable pageable);
    @Query("SELECT obj FROM Movie obj JOIN FETCH obj.genre WHERE obj IN :movies")
    List<Movie> findProductsWithCategories (List<Movie> movies);
}
