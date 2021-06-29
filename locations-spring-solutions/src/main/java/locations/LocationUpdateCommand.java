package locations;

import lombok.Data;

@Data
public class LocationUpdateCommand {
    private String name;
    private double lat;
    private double lon;
}
