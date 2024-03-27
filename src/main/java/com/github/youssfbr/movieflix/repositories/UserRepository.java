package com.github.youssfbr.movieflix.repositories;

import com.github.youssfbr.movieflix.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
