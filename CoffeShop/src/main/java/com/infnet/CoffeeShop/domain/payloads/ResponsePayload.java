package com.infnet.CoffeeShop.domain.payloads;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infnet.CoffeeShop.domain.ResponseKanye;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ResponsePayload {
    private String message;
    private LocalDateTime dataHora;
    private String Yeyquote;
    public ResponsePayload(String message, String json) throws JsonProcessingException {
        this.message = message;
        this.dataHora = LocalDateTime.now();
        ObjectMapper mapper = new ObjectMapper();
        ResponseKanye map = mapper.readValue(json, ResponseKanye.class);
        this.Yeyquote = map.getQuote();
    }
}
