package fasttrackit.course9.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Document(collection = "Cleanup")
public class Cleanup {

    @Id
    private String id;

    private LocalDate data;
    List<CleaningProcedure> proceduri;

    private String roomId;
}
