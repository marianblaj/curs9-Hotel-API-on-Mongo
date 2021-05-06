package fasttrackit.course9.service.cleanup;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import fasttrackit.course9.exception.ResourceNotFoundException;
import fasttrackit.course9.model.entity.CleaningProcedure;
import fasttrackit.course9.model.entity.Cleanup;
import fasttrackit.course9.repository.cleanup.CleanupRepository;
import fasttrackit.course9.service.room.RoomValidator;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CleanupService {

    private final CleanupRepository repo;
    private final RoomValidator roomValidator;
    private final CleanupValidator cleanupValidator;
    private final ObjectMapper mapper;

    public Page<Cleanup> getCleanupRoomId(String roomId, Pageable pageable) {
        roomValidator.validateExistsOrThrow(roomId);
        return repo.findCleanupByRoomId(roomId, pageable);
    }

    @SneakyThrows
    public Cleanup addCleanup(Cleanup newCleanup, String roomId) {
        roomValidator.validateExistsOrThrow(roomId);
        return repo.save(newCleanup);
    }

    @SneakyThrows
    public Cleanup patchCleanup(JsonPatch patch, String cleanupId) {
        cleanupValidator.validateExistsOrThrow(cleanupId);
        Cleanup dbCleanup = repo.findById(cleanupId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn`t find cleanup with id " + cleanupId));
        JsonNode patchedCleanupJson = patch.apply(mapper.valueToTree(dbCleanup));
        Cleanup patchedCleanup = mapper.treeToValue(patchedCleanupJson, Cleanup.class);
        return replaceCleanup(cleanupId, patchedCleanup);
    }

    private Cleanup replaceCleanup(String cleanupId, Cleanup newCleanup) {
        newCleanup.setId(cleanupId);
        Cleanup dbCleanup = repo.findById(cleanupId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn`t find cleanup with id " + cleanupId));
        copyCleanup(newCleanup, dbCleanup);
        return repo.save(dbCleanup);
    }

    private void copyCleanup(Cleanup newCleanup, Cleanup dbCleanup) {
        dbCleanup.setData(newCleanup.getData());
        dbCleanup.setRoomId(newCleanup.getRoomId());
        dbCleanup.setProceduri(List.of(
                CleaningProcedure.builder()
                        .name(newCleanup.getProceduri().get(0).getName())
                        .outcome(newCleanup.getProceduri().get(0).getOutcome())
                        .build(),
                CleaningProcedure.builder()
                        .name(newCleanup.getProceduri().get(1).getName())
                        .outcome(newCleanup.getProceduri().get(1).getOutcome())
                        .build()));
    }

    public void deleteCleanup(String cleanupId) {
        cleanupValidator.validateExistsOrThrow(cleanupId);
        repo.deleteById(cleanupId);
    }
}

