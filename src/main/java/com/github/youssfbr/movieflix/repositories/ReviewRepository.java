package com.github.youssfbr.movieflix.repositories;

import com.github.youssfbr.movieflix.entities.Review;
import com.github.youssfbr.movieflix.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT obj FROM Review obj WHERE "
            + "(obj.user = :user) AND "
            + "(obj.movie.id = :id) ")
    List<Review> find(User user, Long id);

}

