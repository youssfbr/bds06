package com.github.youssfbr.movieflix.services;

import com.github.youssfbr.movieflix.dtos.ReviewDTO;
import com.github.youssfbr.movieflix.entities.Movie;
import com.github.youssfbr.movieflix.entities.Review;
import com.github.youssfbr.movieflix.entities.User;
import com.github.youssfbr.movieflix.repositories.MovieRepository;
import com.github.youssfbr.movieflix.repositories.ReviewRepository;
import com.github.youssfbr.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {
	private final AuthService authService;
	private final MovieRepository movieRepository;
	private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository , MovieRepository movieRepository , AuthService authService) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
	public List<ReviewDTO> reviewsForCurrentUser(Long id) {

		final Optional<Movie> byId = movieRepository.findById(id);
		if (byId.isEmpty()) throw new ResourceNotFoundException("Entity not found.");

		final User user = authService.authenticated();
		final List<Review> list = reviewRepository.find(user, id);

		return list.stream()
				.map(review -> new ReviewDTO(review, user))
				.collect(Collectors.toList());
	}

	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {

		final User user = authService.authenticated();

		final Review entity = new Review();

		copyDtoToEntity(dto, entity);

		final Review reviewSaved = reviewRepository.save(entity);

		return new ReviewDTO(reviewSaved, user);
	}

	private void copyDtoToEntity(ReviewDTO dto, Review entity) {
		entity.setText(dto.getText());
		entity.setMovie(new Movie(dto.getMovieId(), null, null, null, null, null, null));
	}
}
