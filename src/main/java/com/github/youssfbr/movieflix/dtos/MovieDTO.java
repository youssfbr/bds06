package com.github.youssfbr.movieflix.dtos;

import com.github.youssfbr.movieflix.entities.Movie;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MovieDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank(message = "Campo obrigatório")
    private String title;
    private String subTitle;
    @Positive(message = "Ano deve ser um valor positivo")
    private Integer year;
    private String imgUrl;
    private String synopsis;
    private GenreDTO genre;

    private final List<ReviewDTO> reviews = new ArrayList<>();

    public MovieDTO() { }

    public MovieDTO(Long id , String title , String subTitle , Integer year , String imgUrl , String synopsis , GenreDTO genre) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.year = year;
        this.imgUrl = imgUrl;
        this.synopsis = synopsis;
        this.genre = genre;
    }


    public MovieDTO(Movie entity) {
        id = entity.getId();
        title = entity.getTitle();
        subTitle = entity.getSubTitle();
        year = entity.getYear();
        imgUrl = entity.getImgUrl();
        synopsis = entity.getSynopsis();
        genre = new GenreDTO(entity.getGenre());

        entity.getReviews().forEach(review -> this.reviews.add(new ReviewDTO(review)));
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public Integer getYear() {
        return year;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public GenreDTO getGenre() {
        return genre;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }
}
