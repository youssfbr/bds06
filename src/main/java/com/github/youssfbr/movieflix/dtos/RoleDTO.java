package com.github.youssfbr.movieflix.dtos;

import com.github.youssfbr.movieflix.entities.Role;

import java.io.Serializable;

public class RoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
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
