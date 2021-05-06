package fasttrackit.course9.service.room;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import fasttrackit.course9.exception.ResourceNotFoundException;
import fasttrackit.course9.model.RoomFilters;
import fasttrackit.course9.model.entity.Room;
import fasttrackit.course9.model.entity.RoomFacilities;
import fasttrackit.course9.repository.room.RoomDao;
import fasttrackit.course9.repository.room.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomDao roomDao;
    private final RoomRepository repo;
    private final RoomValidator validator;
    private final ObjectMapper mapper;

    public Page<Room> getAll(RoomFilters roomFilters) {
        return roomDao.getAllWithFilters(roomFilters);
    }

    public Optional<Room> getRoomId(String roomId) {
        validator.validateExistsOrThrow(roomId);
        return repo.findById(roomId);
    }

    @SneakyThrows
    public Room patchRoom(String roomId, JsonPatch patch) {
        validator.validateExistsOrThrow(roomId);
        Room dbRoom = repo.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn`t find room with id " + roomId));
        JsonNode patchedRoomJson = patch.apply(mapper.valueToTree(dbRoom));
        Room patchedRoom = mapper.treeToValue(patchedRoomJson, Room.class);
        return replaceRoom(roomId, patchedRoom);
    }

    private Room replaceRoom(String roomId, Room newRoom) {
        newRoom.setId(roomId);
        Room dbRoom = repo.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn`t find room with id " + roomId));
        copyRoom(newRoom, dbRoom);
        return repo.save(dbRoom);
    }

    private void copyRoom(Room newRoom, Room dbRoom) {
        dbRoom.setNumber(newRoom.getNumber());
        dbRoom.setEtaj(newRoom.getEtaj());
        dbRoom.setHotelName(newRoom.getHotelName());
        dbRoom.setRoomFacilities(RoomFacilities.builder()
                .tv(newRoom.getRoomFacilities().isTv())
                .doubleBed(newRoom.getRoomFacilities().isDoubleBed()).build());
    }

    public void deleteRoom(String roomId) {
        validator.validateExistsOrThrow(roomId);
        repo.deleteById(roomId);
    }
}
