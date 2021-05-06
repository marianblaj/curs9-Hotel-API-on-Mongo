package fasttrackit.course9.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "RoomFacilities")
public class RoomFacilities {

    private boolean tv;
    private boolean doubleBed;
}
