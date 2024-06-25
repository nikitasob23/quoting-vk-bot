package com.niksob.quoting_vk_bot.util.date;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static final String UTC = "UTC";

    public static String getTimestamp() {
        ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of(UTC));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        return timestamp.format(formatter);
    }
}
