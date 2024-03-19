package com.github.youssfbr.movieflix.dtos;

import com.github.youssfbr.movieflix.entities.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotBlank(message = "Campo obrigatório")
    private String name;
    @Email(message = "Favor entrar com e-mail válido")
    private String email;
    private final Set<RoleDTO> roles = new HashSet<>();

    public UserDTO() {}
    public UserDTO(Long id , String name , String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public UserDTO(User entity) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }
}
