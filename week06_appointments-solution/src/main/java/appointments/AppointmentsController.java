package appointments;


import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
public class AppointmentsController {

    private NavService service;

    public AppointmentsController(NavService service) {
        this.service = service;
    }

    @GetMapping("/api/types")
    public List<CaseTypeDTO> getCaseType(){
        return service.getCaseTypes();
    }

    @PostMapping("/api/appointments")
    public void bookAppointment(@Valid @RequestBody CreateAppointmentCommand command){
        service.bookAppointment(command);
    }
}
