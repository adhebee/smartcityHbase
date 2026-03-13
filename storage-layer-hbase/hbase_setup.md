# HBase Setup Guide

This document explains how HBase was installed and configured.

## 1. Start HBase

Command:

```bash
start-hbase.sh
```

This starts:

- HBase Master
- Region Server
- ZooKeeper

## 2. Open HBase Shell

Command:

```bash
hbase shell
```

This opens the interactive HBase command line interface.

## 3. Verify Cluster Status

Command:

```bash
status
```

Example output:

```
1 active master, 0 backup masters, 1 servers, 0 dead
```

This confirms the HBase cluster is running.

## 4. Create Table

Command:

```bash
create 'sensor_data', 'metrics'
```

This creates the table used to store sensor data.

## 5. Describe Table Schema

Command:

```bash
describe 'sensor_data'
```

This confirms the column family configuration.

## 6. Insert Test Data

Commands used:

```bash
put 'sensor_data', 'test1', 'metrics:temperature', '30'  
put 'sensor_data', 'test1', 'metrics:humidity', '55'  
put 'sensor_data', 'test1', 'metrics:aqi', '110'
```

## 7. Verify Data

Command:

```bash
scan 'sensor_data'
```

This confirms that data is stored correctly in HBase.