package com.unison.batch.mapper;

public class WindSpeedNtfConverter{

    private static final Double U151_ROTOR_HEIGHT = 100D; // m

    public static Double convertU151WindSpeedNtf(Double windSpeed, Double nacOutTmp) {

        double ntfWindSpeed;

        windSpeed = windSpeed * (6.142912/5) - 0.44362;

        Double airDensity = calcAirDensity(U151_ROTOR_HEIGHT, nacOutTmp);

        U151NtfTable u151NtfTable = U151NtfTable.getNtfTable(windSpeed);

        ntfWindSpeed = u151NtfTable.getSlope() * windSpeed + u151NtfTable.getOffset();
        ntfWindSpeed *= Math.pow(airDensity/1.225, 1.0 / 3.0);

        return ntfWindSpeed;
    }

    public static Double calcAirDensity(Double rotorHeight, Double nacOutTmp){
        return 101325 * Math.pow(1.0 - 0.0065 * (rotorHeight) / 288.15, 9.806565 * 0.0289644 / (8.31447 * 0.0065)) / (287.058 * ( nacOutTmp + 273.15));
    }
}
