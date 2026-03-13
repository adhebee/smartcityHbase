package com.smartcity.backend;

import org.springframework.web.bind.annotation.*;

@RestController
public class SensorController {

    private final HBaseService hbaseService;

    public SensorController(HBaseService hbaseService) {
        this.hbaseService = hbaseService;
    }

    @PostMapping("/insert")
    public String insertSensor(@RequestBody SensorData data) {

        System.out.println("Received data from: " + data.getSensorId());

        try {
            hbaseService.insertSensorData(data);
            System.out.println("Data inserted into HBase successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("-----------------------------------");

        return "Data received and stored in HBase";
    }
}