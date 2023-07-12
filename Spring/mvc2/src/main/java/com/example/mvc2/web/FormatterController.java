package com.example.mvc2.web;

import com.example.mvc2.web.validation.form.ItemSaveForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FormatterController {

    private final ObjectMapper objectMapper;

    @PostMapping(value = "/formatter", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ItemSaveForm formatter(@RequestParam("request") final ItemSaveForm request) {
        log.info(request.getItemName());
        log.info(String.valueOf(request.getPrice()));
        log.info(String.valueOf(request.getQuantity()));
        return request;
    }

    @PostMapping(value = "/formatter", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String formatter2(@Valid @RequestBody final ItemSaveForm request) {
        return request.toString();
    }

    @PostMapping(value = "/formatter2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String formattor(@RequestParam("request") final String request) throws JsonProcessingException {
        final ItemSaveForm itemSaveForm = objectMapper.readValue(request, ItemSaveForm.class);
        log.info(itemSaveForm.getItemName());
        log.info(String.valueOf(itemSaveForm.getPrice()));
        log.info(String.valueOf(itemSaveForm.getQuantity()));
        return itemSaveForm.toString();
    }
}
