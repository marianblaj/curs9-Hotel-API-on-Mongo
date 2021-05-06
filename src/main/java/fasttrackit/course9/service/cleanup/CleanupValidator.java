package fasttrackit.course9.service.cleanup;

import fasttrackit.course9.exception.ValidationException;
import fasttrackit.course9.repository.cleanup.CleanupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class CleanupValidator {

    private final CleanupRepository repo;

    private Optional<ValidationException> exists(String cleanupId) {
        return repo.existsById(cleanupId)
                ? empty()
                : Optional.of(new ValidationException(List.of("Cleanup with id " + cleanupId + " does not exist.")));
    }

    public void validateExistsOrThrow(String cleanupId) {
        exists(cleanupId).ifPresent(ex -> {
            throw ex;
        });
    }
}
