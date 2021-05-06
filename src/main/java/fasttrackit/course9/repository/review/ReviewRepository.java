package fasttrackit.course9.repository.review;

import fasttrackit.course9.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    Page<Review> findReviewByRoomId(String roomId, Pageable pageable);
}
