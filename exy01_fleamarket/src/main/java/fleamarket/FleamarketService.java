package fleamarket;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class FleamarketService {
    private final AtomicLong id = new AtomicLong();
    private final List<Advertisement> ads = new ArrayList<>();
    private final ModelMapper modelMapper;

    public FleamarketService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void deleteAll() {
        id.set(0);
        ads.clear();
    }

    public AdvertisementDTO createAd(CreateAdvertisementCommand command) {
        Advertisement ad = new Advertisement(id.incrementAndGet(), command);
        ads.add(ad);
        return modelMapper.map(ad, AdvertisementDTO.class);
    }

    public List<AdvertisementDTO> getAds(Optional<String> category, Optional<String> text) {
        return ads.stream()
                .filter(a -> category.isEmpty() || a.getLumberCategory() == LumberCategory.valueOf(category.get().toUpperCase()))
                .filter(a -> text.isEmpty() || a.getText().toLowerCase().contains(text.get().toLowerCase()))
                .sorted(Advertisement::compareTo)
                .map(a -> modelMapper.map(a, AdvertisementDTO.class))
                .collect(Collectors.toList());

    }

    public AdvertisementDTO updateAd(long id, UpdateAdvertisementCommand command) {
        Advertisement ad = findAdById(id);
        ad.setText(command.getText());
        return modelMapper.map(ad, AdvertisementDTO.class);
    }

    public AdvertisementDTO getAdById(long id) {
        return modelMapper.map(findAdById(id), AdvertisementDTO.class);
    }

    public void deleteById(long id) {
        ads.remove(findAdById(id));
    }

    public void deleteOldestByCategory(Optional<String> category) {
        Map<LumberCategory, Advertisement> map = getOldestByCategory();
        if (category.isPresent()) {
            LumberCategory cat = LumberCategory.valueOf(category.get().toUpperCase());
            ads.remove(map.get(cat));
        } else {
            for (Map.Entry<LumberCategory, Advertisement> entry : map.entrySet()) {
                ads.remove(entry.getValue());
            }
        }

    }

    /////////////////////////////////////

    private Map<LumberCategory, Advertisement> getOldestByCategory() {
        Map<LumberCategory, Advertisement> map = new HashMap<>();
        for (Advertisement ad : ads) {
            if (map.containsKey(ad.getLumberCategory())) {
                if (ad.getTimeStamp().isBefore(map.get(ad.getLumberCategory()).getTimeStamp())) {
                    map.put(ad.getLumberCategory(), ad);
                }
            } else {
                map.put(ad.getLumberCategory(), ad);
            }
        }
        return map;
    }


    private Advertisement findAdById(long id) {
        return ads.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new AdvertisementNotFoundException("Advertisement not found, id= " + id));
    }
}
