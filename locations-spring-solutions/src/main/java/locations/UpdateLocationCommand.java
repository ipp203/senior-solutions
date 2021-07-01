package locations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLocationCommand {
    @NotBlank(message = "Name can not be blank!")
    private String name;

//    @Min(value = -90, message = "Latitude must be greater than -90")
//    @Max(value = 90, message = "Latitude must be less than 90")
    @Coordinate(type=Type.LAT, message = "Latitude must be between -90 and 90!")
    private double lat;

//    @Min(value = -180, message = "Longitude must be greater than -180")
//    @Max(value = 180, message = "Longitude must be less than 180")
    @Coordinate(type=Type.LON, message = "Longitude must be between -180 and 180!")
    private double lon;
}
