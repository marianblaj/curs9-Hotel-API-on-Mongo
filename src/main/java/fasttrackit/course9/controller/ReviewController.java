package fasttrackit.course9.controller;

import com.github.fge.jsonpatch.JsonPatch;
import fasttrackit.course9.model.CollectionResponse;
import fasttrackit.course9.model.PageInfo;
import fasttrackit.course9.model.entity.Review;
import fasttrackit.course9.service.review.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{roomId}")
    CollectionResponse<Review> getReviewsRoomId(@PathVariable String roomId, Pageable pageable) {
        Page<Review> page = reviewService.getReviewsRoomId(roomId, pageable);

        return CollectionResponse.<Review>builder()
                .content(page.getContent())
                .pageInfo(PageInfo.builder()
                        .totalPages(page.getTotalPages())
                        .totalElements(page.getNumberOfElements())
                        .crtPage(page.getNumber())
                        .pageSize(page.getSize())
                        .build())
                .build();
    }

    @PostMapping("/{roomId}")
    Review addReview(@RequestBody Review newReview, @PathVariable String roomId) {
        return reviewService.addReview(newReview, roomId);
    }

    @PatchMapping("/review/{reviewId}")
    Review patchReview(@RequestBody JsonPatch patch, @PathVariable String reviewId) {
        return reviewService.patchReview(patch, reviewId);
    }

    @DeleteMapping("/review/{reviewId}")
    void deleteReview(@PathVariable String reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
