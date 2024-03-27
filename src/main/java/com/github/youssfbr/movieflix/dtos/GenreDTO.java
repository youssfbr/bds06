package com.github.youssfbr.movieflix.dtos;

import com.github.youssfbr.movieflix.entities.Genre;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class GenreDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotBlank(message = "Campo obrigatório")
    @Size(min = 3, max = 60, message = "Deve ter entre 3 a 60 caracteres")
    private String name;

    public GenreDTO() { }

    public GenreDTO(Long id , String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDTO(Genre entity) {
        id = entity.getId();
        name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
