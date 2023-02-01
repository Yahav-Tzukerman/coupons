package com.yahav.coupons.enums;

import java.sql.Timestamp;

public enum Status {
    ACTIVE,
    SUSPENDED(new Timestamp(System.currentTimeMillis()));

    private Timestamp timestamp;

    Status() {
    }

    Status(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
