package fasttrackit.course9.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CleaningProcedure {

    private String name;
    private int outcome;
}
