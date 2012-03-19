package com.dbsvg.common;

public class StringUtils extends org.springframework.util.StringUtils {

	public static final String EMPTY = "";

	public static boolean isBlank(String value) {
		return value == null || value.equals(EMPTY);
	}
}
