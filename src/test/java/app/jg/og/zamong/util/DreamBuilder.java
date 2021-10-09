package app.jg.og.zamong.util;

import app.jg.og.zamong.entity.dream.Dream;

import java.util.UUID;

public class DreamBuilder {

    public static Dream build() {
        UUID dreamId = UUID.randomUUID();

        return new Dream() {
            @Override
            public UUID getUuid() {
                return dreamId;
            }
        };
    }
}
