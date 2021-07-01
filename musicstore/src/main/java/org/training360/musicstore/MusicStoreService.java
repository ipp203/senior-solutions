package org.training360.musicstore;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MusicStoreService {
    private final AtomicLong id = new AtomicLong();

    private final List<Instrument> instruments = new ArrayList<>();

    private final ModelMapper modelMapper;

    public MusicStoreService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<InstrumentDTO> filterInstruments(Optional<String> brand, Optional<Integer> price) {
        List<Instrument> result = instruments.stream()
                .filter(i -> brand.isEmpty() || i.getBrand().equalsIgnoreCase(brand.get()))
                .filter(i -> price.isEmpty() || i.getPrice() == price.get())
                .collect(Collectors.toList());

        Type targetListType = new TypeToken<List<InstrumentDTO>>() {}.getType();
        return modelMapper.map(result, targetListType);

//        return result.stream()
//                .map(i -> modelMapper.map(i, InstrumentDTO.class))
//                .collect(Collectors.toList());
    }

    public void deleteAll() {
        instruments.clear();
        id.set(0);
    }

    public InstrumentDTO createInstrument(CreateInstrumentCommand command) {
        Instrument instrument = new Instrument(id.incrementAndGet(),
                command.getBrand(),
                command.getInstrumentType(),
                command.getPrice(),
                LocalDate.now());

        instruments.add(instrument);
        return modelMapper.map(instrument, InstrumentDTO.class);
    }

    public InstrumentDTO getInstrumentById(long id) {
        return modelMapper.map(findById(id), InstrumentDTO.class);
    }

    public InstrumentDTO updatePriceById(long id, UpdatePriceCommand command) {
        Instrument instrument = findById(id);

        if (instrument.getPrice() != command.getPrice()) {
            instrument.setPrice(command.getPrice());
            instrument.setPostDate(LocalDate.now());
            //mivel ez az eredeti, listaban levo objektum, igy nem kell a listaban levohoz nyulni, hisz ez az
        }
        return modelMapper.map(instrument, InstrumentDTO.class);

    }

    public void deleteById(long id) {
        Instrument instrument = findById(id);
        instruments.remove(instrument);
    }

    /////////////////////////

    private Instrument findById(long id) {
        return instruments.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElseThrow(() -> new InstrumentNotFoundException("Instrument not found, id: " + id));
    }
}
