package appointments;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class NavService {

    private final ModelMapper modelMapper;

    public NavService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private List<CaseType> types = new ArrayList<>(
            List.of(
                    new CaseType("Adóbevallás", "001"),
                    new CaseType("Befizetés", "002")));

    private List<Appointment>appointments = new ArrayList<>();

    public List<CaseType> getTypes() {
        return types;
    }

    public List<CaseTypeDTO> getCaseTypes() {
        Type targetListTYpe = new TypeToken<List<CaseTypeDTO>>(){}.getType();
        return modelMapper.map(types,targetListTYpe);
    }

    public void bookAppointment(CreateAppointmentCommand command) {
        appointments.add(new Appointment(command.getTaxNumber(), command.getInterval(), command.getCaseId()));
    }
}
