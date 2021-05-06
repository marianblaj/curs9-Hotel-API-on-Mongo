package fasttrackit.course9.controller;

import com.github.fge.jsonpatch.JsonPatch;
import fasttrackit.course9.model.CollectionResponse;
import fasttrackit.course9.model.PageInfo;
import fasttrackit.course9.model.RoomFilters;
import fasttrackit.course9.model.entity.Room;
import fasttrackit.course9.service.room.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    CollectionResponse<Room> getAll(RoomFilters roomFilters) {
        Page<Room> page = roomService.getAll(roomFilters);
        return CollectionResponse.<Room>builder()
                .content(page.getContent())
                .pageInfo(PageInfo.builder()
                        .totalPages(page.getTotalPages())
                        .totalElements(page.getNumberOfElements())
                        .crtPage(page.getNumber())
                        .pageSize(page.getSize())
                        .build())
                .build();
    }

    @GetMapping("/{roomId}")
    Optional<Room> getRoomId(@PathVariable String roomId) {
        return roomService.getRoomId(roomId);
    }

    @PatchMapping("/{roomId}")
    Room patchRoom(@RequestBody JsonPatch patch, @PathVariable String roomId) {
        return roomService.patchRoom(roomId, patch);
    }

    @DeleteMapping("/{roomId}")
    void deleteRoom(@PathVariable String roomId) {
        roomService.deleteRoom(roomId);
    }
}
