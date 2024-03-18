package com.github.youssfbr.movieflix.services;

import com.github.youssfbr.movieflix.dtos.RoleDTO;
import com.github.youssfbr.movieflix.dtos.UserDTO;
import com.github.youssfbr.movieflix.dtos.UserInsertDTO;
import com.github.youssfbr.movieflix.entities.Role;
import com.github.youssfbr.movieflix.entities.User;
import com.github.youssfbr.movieflix.repositories.RoleRepository;
import com.github.youssfbr.movieflix.repositories.UserRepository;

import com.github.youssfbr.movieflix.services.exceptions.DatabaseException;
import com.github.youssfbr.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,  RoleRepository roleRepository , BCryptPasswordEncoder passwordEncoder) {//AuthService authService ,
        this.repository = repository;
        this.roleRepository = roleRepository;
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

        entity.getRoles().clear();
        for (RoleDTO roleDTO : dto.getRoles()) {
            final Role role = roleRepository.getOne(roleDTO.getId());
            entity.getRoles().add(role);
        }
    }
}

