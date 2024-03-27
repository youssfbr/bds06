package com.github.youssfbr.movieflix.dtos;

import com.github.youssfbr.movieflix.entities.Review;
import com.github.youssfbr.movieflix.entities.User;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ReviewDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    @NotBlank(message = "Campo obrigatório")
    private String text;
    private Long movieId;
    private UserDTO user;

    public ReviewDTO() { }

    public ReviewDTO(Long id, String text, Long movieId) {
        this.id = id;
        this.text = text;
        this.movieId = movieId;
    }

    public ReviewDTO(Review entity, User user) {
        id = entity.getId();
        text = entity.getText();
        movieId = entity.getMovie().getId();
        this.user = new UserDTO(user);
    }

    public ReviewDTO(Review entity) {
        id = entity.getId();
        text = entity.getText();
        movieId = entity.getMovie().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public UserDTO getUser() {
        return user;
    }

}
