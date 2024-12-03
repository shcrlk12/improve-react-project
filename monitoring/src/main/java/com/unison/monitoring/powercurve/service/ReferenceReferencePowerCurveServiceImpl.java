package com.unison.monitoring.powercurve.service;


import com.unison.monitoring.powercurve.dto.PowerCurveDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PowerCurveServiceImpl implements PowerCurveService{
    @Override
    public List<PowerCurveDto> getPowerCurve(UUID uuid) {
        return null;
    }
}
