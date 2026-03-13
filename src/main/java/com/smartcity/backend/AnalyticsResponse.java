package com.smartcity.backend;

public class AnalyticsResponse {

    private int averageAQI;
    private int maxAQI;
    private int minAQI;
    private String alert;
    private double averageTemperature;
    private String temperatureAnalysis;

    public AnalyticsResponse(int averageAQI, int maxAQI, int minAQI, String alert, 
                             double averageTemperature, String temperatureAnalysis) {
        this.averageAQI = averageAQI;
        this.maxAQI = maxAQI;
        this.minAQI = minAQI;
        this.alert = alert;
        this.averageTemperature = averageTemperature;
        this.temperatureAnalysis = temperatureAnalysis;
    }

    public int getAverageAQI() {
        return averageAQI;
    }

    public int getMaxAQI() {
        return maxAQI;
    }

    public int getMinAQI() {
        return minAQI;
    }

    public String getAlert() {
        return alert;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public String getTemperatureAnalysis() {
        return temperatureAnalysis;
    }
}