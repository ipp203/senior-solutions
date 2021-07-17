package appointments;

import appointments.validators.CaseTypeIdValidation;
import appointments.validators.EndAfterStartInFuture;
import appointments.validators.TaxNumberValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentCommand {

    @TaxNumberValidation
    private String taxNumber;

    @EndAfterStartInFuture
    private Interval interval;

    @CaseTypeIdValidation
    private String caseId;
}
