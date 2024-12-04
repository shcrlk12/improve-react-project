package com.unison.monitoring.common.config;

import com.unison.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Component
public class AlarmMapRegistry {

    private final Map<UUID, Map<String, String>> alarmMapRegistry;

    @Autowired
    public AlarmMapRegistry(
            @Qualifier("u113AlarmMap") Map<String, String> u113AlarmMap,
            @Qualifier("u120AlarmMap") Map<String, String> u120AlarmMap,
            @Qualifier("u151AlarmMap") Map<String, String> u151AlarmMap
    ) {
        // UUID와 알람 맵을 연결
        alarmMapRegistry = Map.of(
                Constants.U113UUID, u113AlarmMap,
                Constants.U120UUID, u120AlarmMap,
                Constants.U151UUID, u151AlarmMap
        );
    }

    public Map<String, String> getAlarmMap(UUID uuid) {
        return alarmMapRegistry.getOrDefault(uuid, Collections.emptyMap());
    }
}
