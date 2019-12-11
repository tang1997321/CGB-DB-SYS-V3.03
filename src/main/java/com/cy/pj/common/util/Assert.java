package com.cy.pj.common.util;

public abstract class Assert {
//	private Assert() {
//	}
	
	public static void isNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isEmpty(String object, String message) {
		if (object == null || "".equals(object.trim())) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isEmpty(Integer[] ids, String message) {
		if (ids == null || ids.length == 0) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isValid(boolean valid, String message) {
		if (!valid) {
			throw new IllegalArgumentException(message);
		}
	}
}
