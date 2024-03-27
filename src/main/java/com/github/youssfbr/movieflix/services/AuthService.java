package com.github.youssfbr.movieflix.services;

import com.github.youssfbr.movieflix.entities.User;
import com.github.youssfbr.movieflix.repositories.UserRepository;
import com.github.youssfbr.movieflix.services.exceptions.ForbiddenException;
import com.github.youssfbr.movieflix.services.exceptions.UnauthorizedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) { this.userRepository = userRepository; }

    @Transactional(readOnly = true)
    public User authenticated() {
        try {
            final String username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(username);
        }
        catch(Exception e){
            throw new UnauthorizedException("Invalid user");
        }
    }

    public void validateSelf(Long userId) {

        User user = authenticated();
        if(!user.getId().equals(userId)) {
            throw new ForbiddenException("Access denied");
        }
    }
}
