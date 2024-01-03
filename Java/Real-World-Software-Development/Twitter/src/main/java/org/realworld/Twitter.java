package org.realworld;

import java.util.Optional;

public class Twitter {
    private final User users;

    public Twitter() {
        this.users = new User();
    }

    public Optional<SenderEndPoint> login(final Long id, final String password, final ReceiverEndPoint receiverEndPoint) {
        if (id == null || password == null) {
            return Optional.empty();
        }

        return Optional.of(new SenderEndPoint() {
        });
    }
}
