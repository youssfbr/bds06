package com.github.youssfbr.movieflix.repositories;

import com.github.youssfbr.movieflix.dtos.MovieDTO;
import com.github.youssfbr.movieflix.entities.Genre;
import com.github.youssfbr.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT obj "
            + "FROM Movie obj "
            + "INNER JOIN obj.genre gen "
            + "WHERE (COALESCE(:genres) IS NULL OR :genres IN gen) AND "
            + "(obj.genre.id = :genreId OR :genreId = 0) "
            + "ORDER by obj.title")
    Page<MovieDTO> find(List<Genre> genres, Long genreId, Pageable pageable);
}
