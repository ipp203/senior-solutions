package locations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class LocationsService {
    private AtomicLong id = new AtomicLong();
    private List<Location> locations = Collections.synchronizedList(new ArrayList<>(List.of(
            new Location(id.incrementAndGet(), "Budapest", 43.0, 42.0),
            new Location(id.incrementAndGet(), "Debrecen", 43.0, 44.0)
    )));


    private ModelMapper modelMapper;

    public LocationsService() {
    }

    @Autowired
    public LocationsService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<LocationDto> getLocations() {
        Type targetListType = new TypeToken<List<LocationDto>>() {}.getType();
        return modelMapper.map(locations, targetListType);
    }

    public List<LocationDto> getLocationByNameFragment(Optional<String> nameFragment) {
        if(nameFragment.isEmpty()){
            return getLocations();
        }else {
            List<Location> result = locations.stream()
                    .filter(l -> l.getName().toLowerCase().contains(nameFragment.get().toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
            Type targetListType = new TypeToken<List<LocationDto>>(){}.getType();
            return modelMapper.map(result, targetListType);
        }
    }

    public LocationDto getLocationById(long id) {
        Location result = getLocation(id);

        return modelMapper.map(result, LocationDto.class);
    }

    public LocationDto createLocation(CreateLocationCommand command) {
        Location location = new Location(id.incrementAndGet(), command.getName(), command.getLat(),command.getLon());
        locations.add(location);
        return modelMapper.map(location,LocationDto.class);
    }

    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
        Location result = getLocation(id);

        result.setName(command.getName());
        result.setLat(command.getLat());
        result.setLon(command.getLon());

        return modelMapper.map(result,LocationDto.class);
    }

    public void deleteLocation(long id) {
        Location location = getLocation(id);

        locations.remove(location);
    }


////////////////////////

    private Location getLocation(long id){
        return locations.stream()
                .filter(l -> l.getId()==id)
                .findFirst()
                .orElseThrow(() -> new LocationNotFoundException("Location not found, id: " + id));
    }


}

