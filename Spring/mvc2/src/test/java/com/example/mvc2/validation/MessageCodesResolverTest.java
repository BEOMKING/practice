package com.example.mvc2.validation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

class MessageCodesResolverTest {

    private final MessageCodesResolver messageCodesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        final String[] messageCodes = messageCodesResolver.resolveMessageCodes("required", "item");

        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        final String[] messageCodes = messageCodesResolver.resolveMessageCodes("required", "item", "itemName", String.class);

        assertThat(messageCodes).containsExactly("required.item.itemName", "required.itemName", "required.java.lang.String", "required");
    }

}
