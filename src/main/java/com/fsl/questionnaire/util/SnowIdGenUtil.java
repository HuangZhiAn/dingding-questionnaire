package com.fsl.questionnaire.util;

public class SnowIdGenUtil {
    private static final long twepoch = 1420041600000L;
    private static final long workerIdBits = 4L;
    private static final long datacenterIdBits = 6L;
    private static final long maxWorkerId = 15L;
    private static final long maxDatacenterId = 63L;
    private static final long sequenceBits = 12L;
    private static final long workerIdShift = 12L;
    private static final long datacenterIdShift = 16L;
    private static final long timestampLeftShift = 22L;
    private static final long sequenceMask = 4095L;
    private static long sequence = 0L;
    private static long lastTimestamp = -1L;

    public SnowIdGenUtil() {
    }

    public static synchronized long nextId(long workerId, long datacenterId) {
        if (workerId <= 15L && workerId >= 0L) {
            if (datacenterId <= 63L && datacenterId >= 0L) {
                long timestamp = timeGen();
                if (timestamp < lastTimestamp) {
                    throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
                } else {
                    if (lastTimestamp == timestamp) {
                        sequence = sequence + 1L & 4095L;
                        if (sequence == 0L) {
                            timestamp = tilNextMillis(lastTimestamp);
                        }
                    } else {
                        sequence = 0L;
                    }

                    lastTimestamp = timestamp;
                    return timestamp - 1420041600000L << 22 | datacenterId << 16 | workerId << 12 | sequence;
                }
            } else {
                throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", 63L));
            }
        } else {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 15L));
        }
    }

    protected static long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = timeGen(); timestamp <= lastTimestamp; timestamp = timeGen()) {
            ;
        }

        return timestamp;
    }

    protected static long timeGen() {
        return System.currentTimeMillis();
    }
}
