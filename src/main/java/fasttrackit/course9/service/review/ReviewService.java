package fasttrackit.course9.service.review;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import fasttrackit.course9.exception.ResourceNotFoundException;
import fasttrackit.course9.model.entity.Review;
import fasttrackit.course9.repository.review.ReviewRepository;
import fasttrackit.course9.service.room.RoomValidator;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository repo;
    private final RoomValidator roomValidator;
    private final ReviewValidator reviewValidator;
    private final ObjectMapper mapper;

    public Page<Review> getReviewsRoomId(String roomId, Pageable pageable) {
        roomValidator.validateExistsOrThrow(roomId);
        return repo.findReviewByRoomId(roomId, pageable);
    }

    @SneakyThrows
    public Review addReview(Review newReview, String roomId) {
        roomValidator.validateExistsOrThrow(roomId);
        return repo.save(newReview);
    }

    @SneakyThrows
    public Review patchReview(JsonPatch patch, String reviewId) {
        reviewValidator.validateExistsOrThrow(reviewId);
        Review dbReview = repo.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn`t find review with id " + reviewId));
        JsonNode patchedReviewJson = patch.apply(mapper.valueToTree(dbReview));
        Review patchedReview = mapper.treeToValue(patchedReviewJson, Review.class);
        return replaceReview(reviewId, patchedReview);
    }

    private Review replaceReview(String reviewId, Review newReview) {
        newReview.setId(reviewId);
        Review dbReview = repo.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn`t find review with id " + reviewId));
        copyReview(newReview, dbReview);
        return repo.save(dbReview);
    }

    private void copyReview(Review newReview, Review dbReview) {
        dbReview.setRoomId(newReview.getRoomId());
        dbReview.setMesaj(newReview.getMesaj());
        dbReview.setRating(newReview.getRating());
        dbReview.setTurist(newReview.getTurist());
    }

    public void deleteReview(String reviewId) {
        reviewValidator.validateExistsOrThrow(reviewId);
        repo.deleteById(reviewId);
    }
}











