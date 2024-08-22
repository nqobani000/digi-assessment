package com.main.agency_booking.utils;

public class StringUtil {
    
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
    public static boolean containsSymbol(String str, String symbol) {
        return !isNullOrEmpty(str) && str.contains(symbol);
    }
}
