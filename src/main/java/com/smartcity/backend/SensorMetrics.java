package com.smartcity.backend;

public class SensorMetrics {
    private int aqi;
    private double temperature;

    public SensorMetrics(int aqi, double temperature) {
        this.aqi = aqi;
        this.temperature = temperature;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
