package locations;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class LocationsService {
//    private AtomicLong id = new AtomicLong();
//    private List<Location> locations = Collections.synchronizedList(new ArrayList<>(List.of(
//            new Location(id.incrementAndGet(), "Budapest", 43.0, 42.0),
//            new Location(id.incrementAndGet(), "Debrecen", 43.0, 44.0)
//    )));

    private static final Logger log = LoggerFactory.getLogger(LocationsService.class);

    private ModelMapper modelMapper;

    private Environment environment;

    private LocationsRepository repository;

    public LocationsService() {
    }

    @Autowired
    public LocationsService(ModelMapper modelMapper, Environment environment, LocationsRepository repository) {
        this.modelMapper = modelMapper;
        this.environment = environment;
        this.repository = repository;
    }

    //    public List<LocationDto> getLocations() {
//        Type targetListType = new TypeToken<List<LocationDto>>() {
//        }.getType();
//        return modelMapper.map(locations, targetListType);
//    }
    public List<LocationDto> getLocations() {
        Type targetListType = new TypeToken<List<LocationDto>>() {
        }.getType();
        List<Location> locations = repository.findAll();
        return modelMapper.map(locations, targetListType);
    }

    //    public List<LocationDto> getLocationByNameFragment(Optional<String> nameFragment) {
//        if (nameFragment.isEmpty()) {
//            return getLocations();
//        } else {
//            List<Location> result = locations.stream()
//                    .filter(l -> l.getName().toLowerCase().contains(nameFragment.get().toLowerCase(Locale.ROOT)))
//                    .collect(Collectors.toList());
//            Type targetListType = new TypeToken<List<LocationDto>>() {
//            }.getType();
//            return modelMapper.map(result, targetListType);
//        }
//    }
    public List<LocationDto> getLocationByNameFragment(Optional<String> nameFragment) {
        if (nameFragment.isEmpty()) {
            return getLocations();
        } else {
            List<Location> result = repository.findAll().stream()
                    .filter(l -> l.getName().toLowerCase().contains(nameFragment.get().toLowerCase(Locale.ROOT)))
                    .collect(Collectors.toList());
            Type targetListType = new TypeToken<List<LocationDto>>() {
            }.getType();
            return modelMapper.map(result, targetListType);
        }
    }

    //    public LocationDto getLocationById(long id) {
//        Location result = getLocation(id);
//
//        return modelMapper.map(result, LocationDto.class);
//    }
    public LocationDto getLocationById(long id) {
        Location result = repository.findById(id).orElseThrow(() -> new LocationNotFoundException("Location not found, id: " + id));

        return modelMapper.map(result, LocationDto.class);
    }

//    public LocationDto createLocation(CreateLocationCommand command) {
//
//        Location location = new Location(id.incrementAndGet(), changeToUpperCaseBasedOnProperty(command.getName()), command.getLat(), command.getLon());
//        locations.add(location);
//
//        log.info("Location created");
//        log.debug("Location created with id: {}", location.getId());
//
//        return modelMapper.map(location, LocationDto.class);
//    }
    @Transactional
    public LocationDto createLocation(CreateLocationCommand command) {

        Location location = new Location(changeToUpperCaseBasedOnProperty(command.getName()), command.getLat(), command.getLon());
        repository.save(location);

        log.info("Location created");
        log.debug("Location created with id: {}", location.getId());

        return modelMapper.map(location, LocationDto.class);
    }

//    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
//        Location result = getLocation(id);
//
//        result.setName(changeToUpperCaseBasedOnProperty(command.getName()));
//        result.setLat(command.getLat());
//        result.setLon(command.getLon());
//
//        log.info("Location updated");
//        log.debug("Location updated, id: {}", result.getId());
//
//        return modelMapper.map(result, LocationDto.class);
//    }
    @Transactional
    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
        Location result = repository.findById(id).orElseThrow(()->new LocationNotFoundException("Location not found, id: " + id));

        result.setName(changeToUpperCaseBasedOnProperty(command.getName()));
        result.setLat(command.getLat());
        result.setLon(command.getLon());

        log.info("Location updated");
        log.debug("Location updated, id: {}", result.getId());

        return modelMapper.map(result, LocationDto.class);
    }

//    public void deleteLocation(long id) {
//        Location location = getLocation(id);
//
//        locations.remove(location);
//    }
    public void deleteLocation(long id) {
        Location result = repository.findById(id).orElseThrow(()->new LocationNotFoundException("Location not found, id: " + id));

        repository.delete(result);
    }


////////////////////////

//    private Location getLocation(long id) {
//        return locations.stream()
//                .filter(l -> l.getId() == id)
//                .findFirst()
//                .orElseThrow(() -> new LocationNotFoundException("Location not found, id: " + id));
//    }

    private String changeToUpperCaseBasedOnProperty(String name) {
        boolean uppercase = environment.getProperty("locations.touppercase").equals("true");
        return uppercase ? name.toUpperCase() : name;
    }


}

