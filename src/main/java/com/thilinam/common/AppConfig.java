package com.thilinam.common;

public class AppConfig {
    public static final String BOOTSTRAP_SERVER="localhost:29092";
    public static final String APPLICATION_CLIENT_ID="mqtt-demo";
    public static final String ACKS_CONFIG="all";
    public static final String IDEMPOTENCE_CONFIG="true";
    public static final String KAFKA_GROUP_CONSUMER_GROUP="COMMON_KAFKA_CONSUMER_GROUP";
    public static final int DEFAULT_PARTITION_COUNT=3;
    public static final int DEFAULT_REPLICATION_FACTOR=3;
}
