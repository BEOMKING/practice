package org.realworld;

import java.util.Map;

public class Document {
    private final Map<String, String> attributes;

    Document(final Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public String getAttribute(final String name) {
        return attributes.get(name);
    }
}
