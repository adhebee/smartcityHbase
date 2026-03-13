package com.smartcity.backend;

public class SensorData {

    private String sensorId;
    private String city;
    private String zone;
    private double temperature;
    private double humidity;
    private int aqi;
    private double co2;
    private String timestamp;

    // Getters and Setters
    public String getSensorId() { return sensorId; }
    public void setSensorId(String sensorId) { this.sensorId = sensorId; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }

    public int getAqi() { return aqi; }
    public void setAqi(int aqi) { this.aqi = aqi; }

    public double getCo2() { return co2; }
    public void setCo2(double co2) { this.co2 = co2; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}