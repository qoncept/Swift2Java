package jp.co.qoncept.swift;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jp.co.qoncept.functional.BiFunction;
import jp.co.qoncept.functional.Function;
import jp.co.qoncept.functional.Predicate;

public class Swift {
	private Swift() {
	}

	public static <T, R> List<R> map(Iterable<T> source,
			Function<T, R> transform) {
		List<R> result = new ArrayList<R>();

		for (T element : source) {
			result.add(transform.apply(element));
		}

		return result;
	}

	public static <K, V, R> List<R> map(Map<K, V> source,
			Function<Map.Entry<K, V>, R> transform) {
		return map(source.entrySet(), transform);
	}

	public static <T> List<T> filter(Iterable<T> source,
			Predicate<T> includeElement) {
		List<T> result = new ArrayList<T>();

		for (T element : source) {
			if (includeElement.test(element)) {
				result.add(element);
			}
		}

		return result;
	}

	public static <K, V> List<Map.Entry<K, V>> filter(Map<K, V> source,
			Predicate<Map.Entry<K, V>> includeElement) {
		return filter(source.entrySet(), includeElement);
	}

	public static <T, R> R reduce(Iterable<T> source, R initial,
			BiFunction<R, T, R> combine) {
		R result = initial;

		for (T element : source) {
			result = combine.apply(result, element);
		}

		return result;
	}

	public static <K, V, R> R reduce(Map<K, V> source, R initial,
			BiFunction<R, Map.Entry<K, V>, R> combine) {
		return reduce(source.entrySet(), initial, combine);
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

	public static <T, R> R q(T object, Function<T, R> transform) {
		return object == null ? null : transform.apply(object);
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
