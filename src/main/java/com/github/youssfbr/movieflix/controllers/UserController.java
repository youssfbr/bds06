package com.github.youssfbr.movieflix.controllers;

import com.github.youssfbr.movieflix.dtos.UserDTO;
import com.github.youssfbr.movieflix.dtos.UserInsertDTO;
import com.github.youssfbr.movieflix.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAllPaged(Pageable pageable) {
        return ResponseEntity.ok(userService.findAllPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {

        final UserDTO dtoCreated = userService.insert(dto);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dtoCreated.getId()).toUri();

        return ResponseEntity.created(uri).body(dtoCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserInsertDTO dto) {
        return ResponseEntity.ok(userService.update(id , dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
