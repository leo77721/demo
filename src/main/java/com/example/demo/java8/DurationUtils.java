/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.example.demo.java8;

import java.time.*;
import java.util.Date;

/**
 * @author Administrator
 * @date 2020/6/30 17:05
 */
public class DurationUtils {
    public static void main(String[] args) {

        LocalDate startDate = LocalDate.of(2015, 2, 20);
        LocalDate endDate = LocalDate.of(2017, 1, 15);
        Period period = Period.between(startDate, endDate);
        period.isNegative();

        Instant start = Instant.parse("2017-10-03T10:15:30.00Z");
        Instant end = Instant.parse("2017-10-03T10:16:30.00Z");

        Instant instant = new Date().toInstant();
        Duration duration = Duration.between(start, end);

        long sec = duration.getSeconds();
        boolean b = duration.isNegative();

        LocalTime start1 = LocalTime.of(1, 20, 25, 1024);
        LocalTime end1 = LocalTime.of(3, 22, 27, 1544);

        long seconds = Duration.between(start1, end1).getSeconds();

        Duration fromDays = Duration.ofDays(1);
        Duration fromMinutes = Duration.ofMinutes(60);
        Duration fromChar1 = Duration.parse("P1DT1H10M10.5S");
        Duration fromChar2 = Duration.parse("PT10M");
    }
}