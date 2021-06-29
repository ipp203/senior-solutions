package locations;

import lombok.Data;

@Data
public class LocationDto {
    private long id;
    private String name;
    private double lat;
    private double lon;
}
