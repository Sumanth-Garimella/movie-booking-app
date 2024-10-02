package com.fse.moviebookingapp.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fse.moviebookingapp.dto.TicketDto;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TicketDtoDeserializer extends JsonDeserializer<TicketDto> {

    @Override
    public TicketDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectNode objectNode = new ObjectNode(null);
        if (node.isTextual()) {
            String jsonString = node.asText();
            ObjectMapper objectMapper = new ObjectMapper();
            objectNode = (ObjectNode) objectMapper.readTree(jsonString);
        } else if (node.isObject()) {
            objectNode = (ObjectNode) node;
        }
        String movieName = objectNode.get("movieName").asText();
        String theatreName = objectNode.get("theatreName").asText();
        int numberOfTickets =objectNode.has("numberOfTickets")&&!objectNode.get("numberOfTickets").isNull()? objectNode.get("numberOfTickets").asInt():0;
        List<String> seatNumber = new ArrayList<>();
        JsonNode seatNumberNode = objectNode.get("seatNumber");
        if (seatNumberNode != null && seatNumberNode.isArray()) {
            for (JsonNode seat : seatNumberNode) {
                seatNumber.add(seat.asText());
            }
        }
        log.info("Deserialised the Message: {}", objectNode);
        return new TicketDto(movieName, theatreName, numberOfTickets, seatNumber);
    }
}
