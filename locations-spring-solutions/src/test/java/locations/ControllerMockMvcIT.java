package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocationsController.class)
public class ControllerMockMvcIT {
    @MockBean
    LocationsService service;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testPost() throws Exception {
        when(service.getLocationByNameFragment(any())).thenReturn(List.of(
                new LocationDto(1L,"Eger",40.1,40.2),
                new LocationDto(2L,"Pest",42.2,42.3)
        ));
        mockMvc.perform(get("/locations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name",equalTo("Eger")));
    }
}
