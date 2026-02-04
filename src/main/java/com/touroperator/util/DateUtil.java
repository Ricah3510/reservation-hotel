package com.touroperator.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtil {
    
    public static boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return date1.toLocalDate().equals(date2.toLocalDate());
    }
    
    public static LocalDateTime getStartOfDay(LocalDate date) {
        return date.atStartOfDay();
    }
    
    public static LocalDateTime getEndOfDay(LocalDate date) {
        return date.plusDays(1).atStartOfDay();
    }
    
    public static boolean isBetween(LocalDateTime date, LocalDateTime start, LocalDateTime end) {
        if (date == null || start == null || end == null) {
            return false;
        }
        return (date.isAfter(start) || date.isEqual(start)) && (date.isBefore(end) || date.isEqual(end));
    }
    
    public static LocalDateTime addMinutes(LocalDateTime date, long minutes) {
        if (date == null) {
            return null;
        }
        return date.plusMinutes(minutes);
    }
    
    public static long differenceInMinutes(LocalDateTime date1, LocalDateTime date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        return java.time.Duration.between(date1, date2).toMinutes();
    }
    
    public static boolean isFuture(LocalDateTime date) {
        if (date == null) {
            return false;
        }
        return date.isAfter(LocalDateTime.now());
    }
    
    public static boolean isPast(LocalDateTime date) {
        if (date == null) {
            return false;
        }
        return date.isBefore(LocalDateTime.now());
    }
}