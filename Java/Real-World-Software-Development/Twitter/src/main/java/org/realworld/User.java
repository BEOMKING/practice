package org.realworld;

import java.util.HashMap;
import java.util.Map;

public class User {
    final Map<Long, String> users = new HashMap<>();

    public void register(final Map<String, Object> request) {
        users.put((Long) request.get("id"), (String) request.get("password"));
    }

    public int getCount() {
        return users.size();
    }
}
