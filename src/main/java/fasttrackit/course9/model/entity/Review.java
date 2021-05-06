package fasttrackit.course9.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "Review")
public class Review {

    @Id
    private String id;

    private String mesaj;
    private int rating;
    private String turist;

    private String roomId;
}
