package com.smartcity.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnalyticsController {

    @GetMapping("/analytics")
    public AnalyticsResponse getAnalytics() throws Exception {

        AnalyticsService service = new AnalyticsService();
        List<SensorMetrics> metrics = service.getMetrics();

        if (metrics.isEmpty()) {
            return new AnalyticsResponse(0, 0, 0, "No Sensor Data Available", 0.0, "No temperature data available at the moment.");
        }

        int sumAqi = 0;
        int maxAqi = Integer.MIN_VALUE;
        int minAqi = Integer.MAX_VALUE;
        double sumTemp = 0.0;

        for (SensorMetrics metric : metrics) {
            int aqi = metric.getAqi();
            sumAqi += aqi;
            maxAqi = Math.max(maxAqi, aqi);
            minAqi = Math.min(minAqi, aqi);
            sumTemp += metric.getTemperature();
        }

        int avgAqi = sumAqi / metrics.size();
        double avgTemp = sumTemp / metrics.size();
        
        // Round to 2 decimal places
        avgTemp = Math.round(avgTemp * 100.0) / 100.0;

        String alert;

        if (maxAqi > 200)
            alert = "Severe Pollution";
        else if (maxAqi > 150)
            alert = "High Pollution";
        else
            alert = "Normal";

        String tempAnalysis = "";
        if (avgTemp > 35) {
            tempAnalysis = "Extreme heat alert! Please stay hydrated.\nAvoid prolonged outdoor exposure during peak hours.";
        } else if (avgTemp > 25) {
            tempAnalysis = "Warm and pleasant conditions currently.\nOptimal time for outdoor activities, carry water.";
        } else if (avgTemp > 15) {
            tempAnalysis = "Cool and moderate temperature.\nComfortable climate for daily commuting.";
        } else {
            tempAnalysis = "Cold conditions detected.\nPlease wear warm clothing if heading outdoors.";
        }

        return new AnalyticsResponse(avgAqi, maxAqi, minAqi, alert, avgTemp, tempAnalysis);
    }
}