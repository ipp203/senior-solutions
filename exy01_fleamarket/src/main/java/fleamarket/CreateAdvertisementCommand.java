package fleamarket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdvertisementCommand {

    @NotNull
    private LumberCategory lumberCategory;

    @NotBlank
    private String text;
}
