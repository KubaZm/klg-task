package pl.klgsolutions.klgtask.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.klgsolutions.klgtask.objectforrent.dto.ObjectForRentDto;
import pl.klgsolutions.klgtask.person.dto.PersonDto;
import pl.klgsolutions.klgtask.reservation.dto.CreateReservationRequest;
import pl.klgsolutions.klgtask.reservation.dto.ReservationDto;
import pl.klgsolutions.klgtask.reservation.exceptions.ObjectDoesntExistException;
import pl.klgsolutions.klgtask.reservation.exceptions.PersonDoesntExistException;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationService reservationService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(reservationController)
                .build();
    }

    @Test
    public void getReservationsByExistingRenterTest() throws Exception {
        //given
        String renterName = "test name";
        PersonDto renter = PersonDto.builder()
                .name(renterName)
                .build();

        ReservationDto reservation = ReservationDto.builder()
                .renter(renter)
                .build();

        when(reservationService.getReservationsByRenter(renterName)).thenReturn(List.of(reservation));

        //when
        mockMvc.perform(get(String.format("/reservations/renter/%s", renterName)))
                //then
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].renter.name", is(renterName))
                );

    }

    @Test
    public void getReservationsNotExistingRenterTest() throws Exception {
        //given
        String renterName = "not existing name";

        when(reservationService.getReservationsByRenter(renterName)).thenThrow(PersonDoesntExistException.class);

        //when
        mockMvc.perform(get(String.format("/reservations/renter/%s", renterName)))
                //then
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isNotFound(),
                        response -> assertTrue(response.getResolvedException() instanceof PersonDoesntExistException)
                );

    }

    @Test
    public void getReservationsByExistingObjectTest() throws Exception {
        //given
        String objectName = "test name";
        ObjectForRentDto object = ObjectForRentDto.builder()
                .name(objectName)
                .build();

        ReservationDto reservation = ReservationDto.builder()
                .object(object)
                .build();

        when(reservationService.getReservationsByObject(objectName)).thenReturn(List.of(reservation));

        //when
        mockMvc.perform(get(String.format("/reservations/object/%s", objectName)))
                //then
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].object.name", is(objectName))
                );

    }

    @Test
    public void getReservationsNotExistingObjectTest() throws Exception {
        //given
        String objectName = "not existing name";

        when(reservationService.getReservationsByObject(objectName)).thenThrow(ObjectDoesntExistException.class);

        //when
        mockMvc.perform(get(String.format("/reservations/object/%s", objectName)))
                //then
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isNotFound(),
                        response -> assertTrue(response.getResolvedException() instanceof ObjectDoesntExistException)
                );

    }

    @Test
    public void createReservationTest() throws Exception {
        //given
        Long id = 1L;
        String objectName = "object name";
        String landlordName = "landlord name";
        String renterName = " renter name";
        Date startDate = Date.valueOf("2023-06-01");
        Date endDate = Date.valueOf("2023-06-10");
        Float cost = 1000f;

        CreateReservationRequest request = CreateReservationRequest.builder()
                .object(ObjectForRentDto.builder().name(objectName).build())
                .landlord(PersonDto.builder().name(landlordName).build())
                .renter(PersonDto.builder().name(renterName).build())
                .startDate(startDate)
                .endDate(endDate)
                .cost(cost)
                .build();

        ReservationDto response = ReservationDto.builder()
                .id(id)
                .object(ObjectForRentDto.builder().name(objectName).build())
                .landlord(PersonDto.builder().name(landlordName).build())
                .renter(PersonDto.builder().name(renterName).build())
                .startDate(startDate)
                .endDate(endDate)
                .cost(cost)
                .build();

        when(reservationService.createReservation(any(CreateReservationRequest.class))).thenReturn(response);

        //when
        mockMvc.perform(post("/reservations")
                        .contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8))
                        .content(new ObjectMapper().writeValueAsString(request))
                )
                //then
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id", is(1)),
                        jsonPath("$.object.name", is(objectName)),
                        jsonPath("$.landlord.name", is(landlordName)),
                        jsonPath("$.renter.name", is(renterName)),
                        jsonPath("$.startDate", is(startDate.getTime())),
                        jsonPath("$.endDate", is(endDate.getTime())),
                        jsonPath("$.cost", is(1000.0))
                );
    }

    @Test
    public void updateReservationTest() throws Exception {
        //given
        Long id = 1L;
        String objectName = "object name";
        String landlordName = "landlord name";
        String renterName = " renter name";
        Date startDate = Date.valueOf("2023-06-01");
        Date endDate = Date.valueOf("2023-06-10");
        Float cost = 1000f;

        ReservationDto reservation = ReservationDto.builder()
                .id(id)
                .object(ObjectForRentDto.builder().name(objectName).build())
                .landlord(PersonDto.builder().name(landlordName).build())
                .renter(PersonDto.builder().name(renterName).build())
                .startDate(startDate)
                .endDate(endDate)
                .cost(cost)
                .build();

        when(reservationService.updateReservation(any(ReservationDto.class))).thenReturn(reservation);

        //when
        mockMvc.perform(put("/reservations")
                        .contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8))
                        .content(new ObjectMapper().writeValueAsString(reservation))
                )
                //then
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id", is(1)),
                        jsonPath("$.object.name", is(objectName)),
                        jsonPath("$.landlord.name", is(landlordName)),
                        jsonPath("$.renter.name", is(renterName)),
                        jsonPath("$.startDate", is(startDate.getTime())),
                        jsonPath("$.endDate", is(endDate.getTime())),
                        jsonPath("$.cost", is(1000.0))
                );
    }
}
