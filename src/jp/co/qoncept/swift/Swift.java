package jp.co.qoncept.swift;

import jp.co.qoncept.functional.Function;

public class Swift {
	private Swift() {
	}

	public static <T> T as(Object object, Class<T> clazz)
			throws ClassCastException {
		if (object == null) {
			throw new ClassCastException("'object' cannot be null.");
		}

		return clazz.cast(object);
	}

	public static <T> T asq(Object object, Class<T> clazz) {
		try {
			return as(object, clazz);
		} catch (ClassCastException e) {
			return null;
		}
	}

	public static <T, R> R q(T object, Function<T, R> map) {
		return object == null ? null : map.apply(object);
	}

	public static <T> T x(T object) {
		if (object == null) {
			throw new NullPointerException();
		}

		return object;
	}

	public static <T> T qq(T a, T b) {
		return a != null ? a : b;
	}
}
