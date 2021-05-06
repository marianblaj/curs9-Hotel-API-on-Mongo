package fasttrackit.course9.controller;

import com.github.fge.jsonpatch.JsonPatch;
import fasttrackit.course9.model.CollectionResponse;
import fasttrackit.course9.model.PageInfo;
import fasttrackit.course9.model.entity.Cleanup;
import fasttrackit.course9.service.cleanup.CleanupService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/cleanups")
public class CleanupController {

    private final CleanupService cleanupService;

    @GetMapping("/{roomId}")
    CollectionResponse<Cleanup> getCleanupsRoomId(@PathVariable String roomId, Pageable pageable) {
        Page<Cleanup> page = cleanupService.getCleanupRoomId(roomId, pageable);

        return CollectionResponse.<Cleanup>builder()
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
    Cleanup addCleanup(@RequestBody Cleanup newCleanup, @PathVariable String roomId) {
        return cleanupService.addCleanup(newCleanup, roomId);
    }

    @PatchMapping("/cleanup/{cleanupId}")
    Cleanup patchCleanup(@RequestBody JsonPatch patch, @PathVariable String cleanupId) {
        return cleanupService.patchCleanup(patch, cleanupId);
    }

    @DeleteMapping("/cleanup/{cleanupId}")
    void deleteCleanup(@PathVariable String cleanupId) {
        cleanupService.deleteCleanup(cleanupId);
    }
}
