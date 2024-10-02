package com.fse.moviebookingapp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.moviebookingapp.dto.TicketDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class TicketDtoDeserializerTest {

    @Test
    public void testDeserialize() throws JsonProcessingException {
        String json = "{"
                + "\"movieName\": \"Inception\","
                + "\"theatreName\": \"PVR Cinemas\","
                + "\"numberOfTickets\": 2,"
                + "\"seatNumber\": [\"A1\", \"A2\"]"
                + "}";

        ObjectMapper objectMapper = new ObjectMapper();
        TicketDto ticketDto = objectMapper.readValue(json, TicketDto.class);

        // Verify the deserialization
        assertNotNull(ticketDto);
        assertEquals("Inception", ticketDto.getMovieName());
        assertEquals("PVR Cinemas", ticketDto.getTheatreName());
        assertEquals(2, ticketDto.getNumberOfTickets());

        List<String> expectedSeatNumbers = Arrays.asList("A1", "A2");
        assertEquals(expectedSeatNumbers, ticketDto.getSeatNumber());
    }

    @Test
    public void testDeserialize_EmptySeatNumber() throws JsonProcessingException {
        String json = "{"
                + "\"movieName\": \"Inception\","
                + "\"theatreName\": \"PVR Cinemas\","
                + "\"numberOfTickets\": 2,"
                + "\"seatNumber\": []"
                + "}";

        ObjectMapper objectMapper = new ObjectMapper();
        TicketDto ticketDto = objectMapper.readValue(json, TicketDto.class);

        // Verify the deserialization
        assertNotNull(ticketDto);
        assertEquals("Inception", ticketDto.getMovieName());
        assertEquals("PVR Cinemas", ticketDto.getTheatreName());
        assertEquals(2, ticketDto.getNumberOfTickets());

        List<String> expectedSeatNumbers = Arrays.asList();
        assertEquals(expectedSeatNumbers, ticketDto.getSeatNumber());
    }

    @Test
    public void testDeserialize_MissingField() throws JsonProcessingException {
        String json = "{"
                + "\"movieName\": \"Inception\","
                + "\"theatreName\": \"PVR Cinemas\""
                + "}";

        ObjectMapper objectMapper = new ObjectMapper();
        TicketDto ticketDto = objectMapper.readValue(json, TicketDto.class);

        // Verify the deserialization
        assertNotNull(ticketDto);
        assertEquals("Inception", ticketDto.getMovieName());
        assertEquals("PVR Cinemas", ticketDto.getTheatreName());
        assertEquals(0, ticketDto.getNumberOfTickets()); // default int value
        List<String> expectedSeatNumbers = Arrays.asList();
        assertEquals(expectedSeatNumbers, ticketDto.getSeatNumber());
    }
}
