package com.example.mvc2;

import com.example.mvc2.domain.item.Item;
import com.example.mvc2.web.validation.form.ItemSaveForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemFormatter implements Formatter<ItemSaveForm> {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public ItemSaveForm parse(final String text, final Locale locale) throws RuntimeException {
        log.info("text={}, locale={}", text, locale);
        return objectMapper.readValue(text, ItemSaveForm.class);
    }

    @Override
    public String print(final ItemSaveForm object, final Locale locale) {
        return "Not Implemented";
    }
}
