package fasttrackit.course9.service.review;

import fasttrackit.course9.exception.ValidationException;
import fasttrackit.course9.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    private final ReviewRepository repo;

    private Optional<ValidationException> exists(String reviewId) {
        System.out.println(repo.findAll());
        return repo.existsById(reviewId)
                ? empty()
                : Optional.of(new ValidationException(List.of("Review with id " + reviewId + " does not exist.")));
    }

    public void validateExistsOrThrow(String reviewId) {
        exists(reviewId).ifPresent(ex -> {
            throw ex;
        });
    }
}
