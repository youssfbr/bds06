package com.github.youssfbr.movieflix.dtos;

import com.github.youssfbr.movieflix.entities.Role;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    private String authority;

    public RoleDTO() { }

    public RoleDTO(Long id , String authority) {
        this.id = id;
        this.authority = authority;
    }

    public RoleDTO(Role role) {
        id = role.getId();
        authority = role.getAuthority();
    }

    public Long getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }
}
