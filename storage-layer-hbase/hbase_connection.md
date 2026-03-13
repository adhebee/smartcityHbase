# Backend Connection Configuration

The backend API connects to HBase using the HBase Java client.

## HBase Server Details

Host IP: 172.21.146.140  
ZooKeeper Port: 2181

## Backend Configuration Example

Java configuration used by backend:

```java
Configuration config = HBaseConfiguration.create();
config.set("hbase.zookeeper.quorum", "172.21.146.140");

Connection connection = ConnectionFactory.createConnection(config);
```

## Data Flow

Sensor Simulator → Backend API → HBase

The backend inserts sensor readings into the sensor_data table.