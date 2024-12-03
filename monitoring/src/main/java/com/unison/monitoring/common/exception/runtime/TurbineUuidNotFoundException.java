package com.unison.monitoring.common.exception;

import java.util.UUID;

public class TurbineUuidNotFoundException extends RuntimeException{

    public TurbineUuidNotFoundException(String table, UUID uuid) {
        super("cannot found turbine uuid in " + table + " table." + "[ " + uuid.toString() + " ]");
    }
}
