package com.smartcity.backend;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

public class AnalyticsService {

    public List<SensorMetrics> getMetrics() throws Exception {

        List<SensorMetrics> metricsList = new ArrayList<>();

        Connection connection = ConnectionFactory.createConnection(HBaseConfig.getConfig());

        Table table = connection.getTable(
                org.apache.hadoop.hbase.TableName.valueOf("sensor_data"));

        Scan scan = new Scan();

        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {

            byte[] aqiValue = result.getValue(
                    Bytes.toBytes("metrics"),
                    Bytes.toBytes("aqi"));
                    
            byte[] tempValue = result.getValue(
                    Bytes.toBytes("metrics"),
                    Bytes.toBytes("temperature"));

            if (aqiValue != null && tempValue != null) {
                int aqi = Integer.parseInt(Bytes.toString(aqiValue));
                double temperature = Double.parseDouble(Bytes.toString(tempValue));
                metricsList.add(new SensorMetrics(aqi, temperature));
            }
        }

        scanner.close();
        table.close();
        connection.close();

        return metricsList;
    }
}