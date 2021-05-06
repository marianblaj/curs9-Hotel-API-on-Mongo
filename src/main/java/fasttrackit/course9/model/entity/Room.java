package fasttrackit.course9.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "Room")
public class Room {

    @Id
    String id;

    private String number;
    private int etaj;
    private String hotelName;

    private RoomFacilities roomFacilities;
}
