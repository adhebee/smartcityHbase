{\rtf1\ansi\ansicpg1252\cocoartf2867
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 # HBase Setup Guide\
\
This document explains how HBase was installed and configured.\
\
## 1. Start HBase\
\
Command:\
\
start-hbase.sh\
\
This starts:\
\
- HBase Master\
- Region Server\
- ZooKeeper\
\
## 2. Open HBase Shell\
\
Command:\
\
hbase shell\
\
This opens the interactive HBase command line interface.\
\
## 3. Verify Cluster Status\
\
Command:\
\
status\
\
Example output:\
\
1 active master, 0 backup masters, 1 servers, 0 dead\
\
This confirms the HBase cluster is running.\
\
## 4. Create Table\
\
Command:\
\
create 'sensor_data', 'metrics'\
\
This creates the table used to store sensor data.\
\
## 5. Describe Table Schema\
\
Command:\
\
describe 'sensor_data'\
\
This confirms the column family configuration.\
\
## 6. Insert Test Data\
\
Commands used:\
\
put 'sensor_data', 'test1', 'metrics:temperature', '30'  \
put 'sensor_data', 'test1', 'metrics:humidity', '55'  \
put 'sensor_data', 'test1', 'metrics:aqi', '110'\
\
## 7. Verify Data\
\
Command:\
\
scan 'sensor_data'\
\
This confirms that data is stored correctly in HBase.}