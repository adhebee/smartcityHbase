# 🗄️ HBase Storage Layer Details

This module implements the **storage layer** for the Smart City Environmental Monitoring System.

## Role in System

The HBase database stores environmental sensor readings sent by the backend API.

**System architecture:**
Simulator → Backend API → HBase → Analytics Service

## Technologies Used

- Apache HBase 2.6.4
- OpenJDK 17
- macOS
- ZooKeeper (port 2181)

## Responsibilities

The storage layer is responsible for:
- Persisting environmental sensor data
- Managing table schema
- Supporting analytics queries
- Ensuring scalable data storage

## Storage Design

**Table Name**: `sensor_data`  
**Column Family**: `metrics`

**Example Columns:**
- `metrics:temperature`
- `metrics:humidity`
- `metrics:aqi`
- `metrics:co2`
- `metrics:city`
- `metrics:zone`
- `metrics:timestamp`

**Row Key Format:**
`sensorId_timestamp`

**Example:**
`S101_202603041230`

---
*Empowering future cities with distributed intelligence.*
