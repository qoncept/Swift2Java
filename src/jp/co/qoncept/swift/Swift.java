package jp.co.qoncept.swift;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.qoncept.functional.BiFunction;
import jp.co.qoncept.functional.Function;
import jp.co.qoncept.functional.Predicate;
import jp.co.qoncept.util.Tuple2;

public class Swift {
	private Swift() {
	}

	public static <T, R> List<R> map(Iterable<T> source,
			final Function<T, R> transform) {
		return reduce(source, new ArrayList<R>(),
				new BiFunction<List<R>, T, List<R>>() {
					@Override
					public List<R> apply(List<R> result, T element) {
						result.add(transform.apply(element));
						return result;
					}
				});
	}

	public static <K, V, R> List<R> map(Map<K, V> source,
			Function<Map.Entry<K, V>, R> transform) {
		return map(source.entrySet(), transform);
	}

	public static <T> List<T> filter(Iterable<T> source,
			final Predicate<T> includeElement) {
		return reduce(source, new ArrayList<T>(),
				new BiFunction<List<T>, T, List<T>>() {
					@Override
					public List<T> apply(List<T> result, T element) {
						if (includeElement.test(element)) {
							result.add(element);
						}
						return result;
					}
				});
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

	public static <T> Iterable<? extends Tuple2<Integer, ? extends T>> enumerate(
			final Iterable<? extends T> iterable) {
		return new Iterable<Tuple2<Integer, ? extends T>>() {
			@Override
			public Iterator<Tuple2<Integer, ? extends T>> iterator() {
				final Iterator<? extends T> iterator = iterable.iterator();
				return new Iterator<Tuple2<Integer, ? extends T>>() {
					private int index = 0;

					@Override
					public boolean hasNext() {
						return iterator.hasNext();
					}

					@Override
					public Tuple2<Integer, ? extends T> next() {
						return new Tuple2<Integer, T>(index++, iterator.next());
					}
				};
			}
		};
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
