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

	public static <T, R> List<R> map(Iterable<? extends T> source,
			final Function<? super T, ? extends R> transform) {
		return reduce(source, new ArrayList<R>(),
				new BiFunction<List<R>, T, List<R>>() {
					@Override
					public List<R> apply(List<R> result, T element) {
						result.add(transform.apply(element));
						return result;
					}
				});
	}

	public static <K, V, R> List<R> map(
			Map<? extends K, ? extends V> source,
			Function<? super Map.Entry<? extends K, ? extends V>, ? extends R> transform) {
		return map(source.entrySet(), transform);
	}

	public static <T, R> R map(T x, Function<? super T, ? extends R> f) {
		return x == null ? null : f.apply(x);
	}

	public static <T, R> List<R> flatMap(Iterable<? extends T> source,
			Function<? super T, ? extends List<R>> transform) {
		return reduce(map(source, transform), new ArrayList<R>(),
				new BiFunction<List<R>, List<R>, List<R>>() {
					@Override
					public List<R> apply(List<R> l, List<R> r) {
						l.addAll(r);
						return l;
					}
				});
	}

	public static <T, R> R flatMap(T source,
			Function<? super T, ? extends R> transform) {
		return map(source, transform);
	}

	public static <T> List<T> filter(Iterable<? extends T> source,
			final Predicate<? super T> includeElement) {
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

	@SuppressWarnings("unchecked")
	public static <K, V> List<Map.Entry<? extends K, ? extends V>> filter(
			Map<K, ? extends V> source,
			Predicate<? super Map.Entry<? extends K, ? extends V>> includeElement) {
		source.entrySet();
		return (List<Map.Entry<? extends K, ? extends V>>) (List<?>) /* for Java 7 or earlier */
		filter(source.entrySet(), includeElement);
	}

	public static <T, R> R reduce(Iterable<? extends T> source, R initial,
			BiFunction<? super R, ? super T, ? extends R> combine) {
		R result = initial;

		for (T element : source) {
			result = combine.apply(result, element);
		}

		return result;
	}

	public static <K, V, R> R reduce(
			Map<? extends K, ? extends V> source,
			R initial,
			BiFunction<? super R, ? super Map.Entry<? extends K, ? extends V>, ? extends R> combine) {
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

					@Override
					public void remove() {
						throw new UnsupportedOperationException("remove");
					}
				};
			}
		};
	}

	public static <T> List<T> plus(List<T> lhs, List<T> rhs) {
		List<T> result = new ArrayList<T>(lhs.size() + rhs.size());
		result.addAll(lhs);
		result.addAll(rhs);

		return result;
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

	public static <T> boolean is(Object object, Class<T> clazz) {
		if (object == null) {
			return false;
		}

		return clazz.isInstance(object);
	}

	public static <T, R> R q(T object,
			Function<? super T, ? extends R> transform) {
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

	public static <T> List<T> array(int count, T repeatedValue) {
		List<T> result = new ArrayList<T>(count);
		for (int i = 0; i < count; i++) {
			result.add(repeatedValue);
		}

		return result;
	}
}
