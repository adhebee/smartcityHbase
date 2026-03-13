package com.smartcity.backend;

import org.springframework.stereotype.Service;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

@Service
public class HBaseService {

    private static final String TABLE_NAME = "sensor_data";
    private static final String COLUMN_FAMILY = "metrics";

    private Connection connection;

    public HBaseService() throws IOException {

        System.out.println("Starting HBase service...");

        Configuration config = HBaseConfiguration.create();

        config.set("hbase.zookeeper.quorum", "172.21.146.140");
        config.set("hbase.zookeeper.property.clientPort", "2181");

        config.set("hbase.master", "172.21.146.140:16000");

        connection = ConnectionFactory.createConnection(config);

        System.out.println("Connected to HBase cluster");

        createTableIfNotExists();
    }

    private void createTableIfNotExists() throws IOException {

        Admin admin = connection.getAdmin();
        TableName tableName = TableName.valueOf(TABLE_NAME);

        if (!admin.tableExists(tableName)) {

            TableDescriptor tableDescriptor =
                    TableDescriptorBuilder.newBuilder(tableName)
                            .setColumnFamily(
                                    ColumnFamilyDescriptorBuilder.of(COLUMN_FAMILY)
                            )
                            .build();

            admin.createTable(tableDescriptor);

            System.out.println("HBase table created: " + TABLE_NAME);

        } else {

            System.out.println("HBase table already exists");
        }

        admin.close();
    }

    public void insertSensorData(SensorData data) throws IOException {

        Table table = connection.getTable(TableName.valueOf(TABLE_NAME));

        String rowKey = data.getSensorId() + "_" + System.currentTimeMillis();

        Put put = new Put(Bytes.toBytes(rowKey));

        put.addColumn(Bytes.toBytes(COLUMN_FAMILY),
                Bytes.toBytes("temperature"),
                Bytes.toBytes(String.valueOf(data.getTemperature())));

        put.addColumn(Bytes.toBytes(COLUMN_FAMILY),
                Bytes.toBytes("humidity"),
                Bytes.toBytes(String.valueOf(data.getHumidity())));

        put.addColumn(Bytes.toBytes(COLUMN_FAMILY),
                Bytes.toBytes("aqi"),
                Bytes.toBytes(String.valueOf(data.getAqi())));

        put.addColumn(Bytes.toBytes(COLUMN_FAMILY),
                Bytes.toBytes("co2"),
                Bytes.toBytes(String.valueOf(data.getCo2())));

        table.put(put);

        System.out.println("Inserted row into HBase: " + rowKey);

        table.close();
    }
}