package com.smartcity.backend;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class HBaseConfig {

    public static Configuration getConfig() {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.zookeeper.quorum", "172.21.146.140");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        return config;
    }
}