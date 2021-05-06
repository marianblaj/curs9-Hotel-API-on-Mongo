package fasttrackit.course9.repository.cleanup;

import fasttrackit.course9.model.entity.Cleanup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleanupRepository extends MongoRepository<Cleanup, String> {
    Page<Cleanup> findCleanupByRoomId(String roomId, Pageable pageable);
}

