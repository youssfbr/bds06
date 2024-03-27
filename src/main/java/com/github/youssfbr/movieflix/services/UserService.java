package com.github.youssfbr.movieflix.services;

import com.github.youssfbr.movieflix.dtos.UserDTO;
import com.github.youssfbr.movieflix.dtos.UserInsertDTO;
import com.github.youssfbr.movieflix.entities.User;
import com.github.youssfbr.movieflix.repositories.UserRepository;
import com.github.youssfbr.movieflix.services.exceptions.DatabaseException;
import com.github.youssfbr.movieflix.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return repository.findById(id)
                .map(UserDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {

        final User entity = new User();
        copyDtoToEntity(dto , entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        final User userSaved = repository.save(entity);
        return new UserDTO(userSaved);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        try {
            final User entity = repository.getOne(id);
            copyDtoToEntity(dto , entity);
            entity.setId(id);
            final User userUpdated = repository.save(entity);
            return new UserDTO(userUpdated);
        }
        catch (Exception e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(UserDTO dto , User entity) {
        BeanUtils.copyProperties(dto , entity);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        final User user = repository.findByEmail(s);

        if (user == null) {
            logger.error("User not found: " + s);
            throw new UsernameNotFoundException("Email not found");
        }
        logger.info("User found: " + s);
        user.getRoles().forEach(role -> logger.info(role.getAuthority()));

        return user;
    }
}