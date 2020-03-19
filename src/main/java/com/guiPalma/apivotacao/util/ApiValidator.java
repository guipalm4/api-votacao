package com.guiPalma.apivotacao.util;

import java.util.List;

public class ApiValidator {
	
	public static boolean has(Object value) {
        return hasValue(value);
    }

    public static boolean has(String value) {
        if (value != null && !value.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static <V extends Number> boolean has(V value) {
        if (value != null && value.longValue() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean has(Boolean value) {
        return hasValue(value) && value.booleanValue();
    }

    public static boolean has(List<?> value) {
        if (value != null && !value.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    private static boolean hasValue(Object value) {
        return value != null;
    }

}
