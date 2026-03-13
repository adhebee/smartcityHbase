{\rtf1\ansi\ansicpg1252\cocoartf2867
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 # Backend Connection Configuration\
\
The backend API connects to HBase using the HBase Java client.\
\
## HBase Server Details\
\
Host IP: 172.21.146.140  \
ZooKeeper Port: 2181\
\
## Backend Configuration Example\
\
Java configuration used by backend:\
\
Configuration config = HBaseConfiguration.create();\
config.set("hbase.zookeeper.quorum", "172.21.146.140");\
\
Connection connection = ConnectionFactory.createConnection(config);\
\
## Data Flow\
\
Sensor Simulator \uc0\u8594  Backend API \u8594  HBase\
\
The backend inserts sensor readings into the sensor_data table.}