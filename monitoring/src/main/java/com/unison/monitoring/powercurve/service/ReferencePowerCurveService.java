package com.unison.monitoring.powercurve.service;

import com.unison.monitoring.powercurve.dto.PowerCurveDto;

import java.util.List;
import java.util.UUID;

public interface ReferencePowerCurveService {
    List<PowerCurveDto> getReferencePowerCurve(UUID uuid);
}
