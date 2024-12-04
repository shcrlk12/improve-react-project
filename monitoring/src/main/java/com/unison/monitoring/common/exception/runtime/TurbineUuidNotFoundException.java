package com.unison.monitoring.common.exception.runtime;

import java.util.UUID;

public class TurbineUuidNotFoundException extends CustomException{

    public TurbineUuidNotFoundException(String table, UUID uuid) {
        super("cannot found turbine uuid in " + table + " table." + "[ " + uuid.toString() + " ]");
    }
}
