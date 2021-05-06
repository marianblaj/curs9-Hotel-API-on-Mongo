package fasttrackit.course9.service.room;

import fasttrackit.course9.exception.ValidationException;
import fasttrackit.course9.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class RoomValidator {

    private final RoomRepository repo;

    private Optional<ValidationException> exists(String roomId) {
        return repo.existsById(roomId)
                ? empty()
                : Optional.of(new ValidationException(List.of("Room with id " + roomId + " does not exist.")));
    }

    public void validateExistsOrThrow(String roomId) {
        exists(roomId).ifPresent(ex -> {
            throw ex;
        });
    }
}

