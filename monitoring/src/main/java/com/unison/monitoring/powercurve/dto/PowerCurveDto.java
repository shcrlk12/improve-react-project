package com.unison.monitoring.powercurve.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class PowerCurveDto {
    private final UUID turbineUuid;
    private final String windSpeed;
    private final String activePower;
}