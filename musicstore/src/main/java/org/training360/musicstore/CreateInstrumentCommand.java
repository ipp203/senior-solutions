package org.training360.musicstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateInstrumentCommand {
    @NotEmpty
    private String brand;

    private InstrumentType instrumentType;
    @Positive
    private int price;
}
