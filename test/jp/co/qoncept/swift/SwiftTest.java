package jp.co.qoncept.swift;

import static jp.co.qoncept.swift.Swift.array;
import static jp.co.qoncept.swift.Swift.as;
import static jp.co.qoncept.swift.Swift.asq;
import static jp.co.qoncept.swift.Swift.enumerate;
import static jp.co.qoncept.swift.Swift.filter;
import static jp.co.qoncept.swift.Swift.flatMap;
import static jp.co.qoncept.swift.Swift.is;
import static jp.co.qoncept.swift.Swift.join;
import static jp.co.qoncept.swift.Swift.map;
import static jp.co.qoncept.swift.Swift.plus;
import static jp.co.qoncept.swift.Swift.q;
import static jp.co.qoncept.swift.Swift.qq;
import static jp.co.qoncept.swift.Swift.reduce;
import static jp.co.qoncept.swift.Swift.x;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import jp.co.qoncept.functional.BiFunction;
import jp.co.qoncept.functional.Function;
import jp.co.qoncept.functional.Predicate;
import jp.co.qoncept.util.Tuple2;

import org.junit.Test;

public class SwiftTest {
	@Test
	public void testMap() {
		{
			// let result = ["123", "456", "789"].map { $0.toInt()! }
			List<Integer> result = map(Arrays.asList("123", "456", "789"),
					new Function<String, Integer>() {
						@Override
						public Integer apply(String t) {
							return Integer.parseInt(t);
						}
					});
			assertEquals(Arrays.asList(123, 456, 789), result);
		}

		{
			Map<Character, Integer> characterToInteger = new HashMap<Character, Integer>();
			characterToInteger.put('A', 1);
			characterToInteger.put('B', 2);
			characterToInteger.put('C', 3);

			List<String> result = map(
					characterToInteger,
					new Function<Map.Entry<? extends Character, ? extends Integer>, String>() {
						@Override
						public String apply(
								Map.Entry<? extends Character, ? extends Integer> t) {
							StringBuilder builder = new StringBuilder();
							for (int i = 0; i < t.getValue(); i++) {
								builder.append(t.getKey());
							}

							return builder.toString();
						}
					});
			assertEquals(Arrays.asList("A", "BB", "CCC"), result);
		}

		{
			// let a: Int? = 3
			// let result = a.map { $0 * $0 }
			Integer a = 3;
			Integer result = map(a, new Function<Integer, Integer>() {
				@Override
				public Integer apply(Integer t) {
					return t * t;
				}
			});
			assertEquals(new Integer(9), result);
		}

		{
			// let a: Int? = nil
			// let result = a.map { $0 * $0 }
			Integer a = null;
			Integer result = map(a, new Function<Integer, Integer>() {
				@Override
				public Integer apply(Integer t) {
					return t * t;
				}
			});
			assertEquals(null, result);
		}
	}

	@Test
	public void testFlatMap() {
		{
			// let result: [Int] = [[2], [3, 5], [7, 11, 13]].flatMap { $0 }
			List<Integer> result = flatMap(
					Arrays.asList(Arrays.asList(2), Arrays.asList(3, 5),
							Arrays.asList(7, 11, 13)),
					new Function<List<Integer>, List<Integer>>() {
						@Override
						public List<Integer> apply(List<Integer> t) {
							return t;
						}
					});
			assertEquals(result, Arrays.asList(2, 3, 5, 7, 11, 13));
		}

		{
			// let result: [Int] = [1, 2, 3].flatMap { [Int](count: $0, repeatedValue: $0) }
			List<Integer> result = flatMap(Arrays.asList(1, 2, 3),
					new Function<Integer, List<Integer>>() {
						@Override
						public List<Integer> apply(Integer t) {
							return array(t, t);
						}
					});
			assertEquals(result, Arrays.asList(1, 2, 2, 3, 3, 3));
		}

		{
			// let a: Int? = 2
			// let b: Int? = 3
			// let result: Int? = a.flatMap { a0 in b.flatMap { b0 in a0 + b0 } }
			Integer a = 2;
			Integer b = 3;

			Integer result = flatMap(a, new Function<Integer, Integer>() {
				@Override
				public Integer apply(final Integer a0) {
					return flatMap(b, new Function<Integer, Integer>() {
						@Override
						public Integer apply(Integer b0) {
							return a0 + b0;
						}
					});
				}
			});
			assertEquals(result, new Integer(5));
		}

		{
			Integer a = null;
			Integer b = 3;

			Integer result = flatMap(a, new Function<Integer, Integer>() {
				@Override
				public Integer apply(final Integer a0) {
					return flatMap(b, new Function<Integer, Integer>() {
						@Override
						public Integer apply(Integer b0) {
							return a0 + b0;
						}
					});
				}
			});
			assertNull(result);
		}

		{
			Integer a = 2;
			Integer b = null;

			Integer result = flatMap(a, new Function<Integer, Integer>() {
				@Override
				public Integer apply(final Integer a0) {
					return flatMap(b, new Function<Integer, Integer>() {
						@Override
						public Integer apply(Integer b0) {
							return a0 + b0;
						}
					});
				}
			});
			assertNull(result);
		}

		{
			Integer a = null;
			Integer b = null;

			Integer result = flatMap(a, new Function<Integer, Integer>() {
				@Override
				public Integer apply(final Integer a0) {
					return flatMap(b, new Function<Integer, Integer>() {
						@Override
						public Integer apply(Integer b0) {
							return a0 + b0;
						}
					});
				}
			});
			assertNull(result);
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFilter() {
		{
			// let result = [123, 456, 789].filter { $0 % 2 == 1 }
			List<Integer> result = filter(Arrays.asList(123, 456, 789),
					new Predicate<Integer>() {
						@Override
						public boolean test(Integer t) {
							return t % 2 == 1;
						}
					});
			assertEquals(Arrays.asList(123, 789), result);
		}

		{
			Map<String, Integer> stringToInteger = new HashMap<String, Integer>();
			stringToInteger.put("a", 1);
			stringToInteger.put("bc", 0);
			stringToInteger.put("def", 3);

			List<Map.Entry<? extends String, ? extends Integer>> result = filter(
					stringToInteger,
					new Predicate<Map.Entry<? extends String, ? extends Integer>>() {
						@Override
						public boolean test(
								Map.Entry<? extends String, ? extends Integer> t) {
							return t.getKey().length() == t.getValue();
						}
					});
			Collections
					.sort(result,
							new Comparator<Map.Entry<? extends String, ? extends Integer>>() {
								@Override
								public int compare(
										Map.Entry<? extends String, ? extends Integer> o1,
										Map.Entry<? extends String, ? extends Integer> o2) {
									return o1.getValue().compareTo(
											o2.getValue());
								}
							});
			assertEquals(Arrays.asList(
					new SimpleEntry<String, Integer>("a", 1),
					new SimpleEntry<String, Integer>("def", 3)), result);
		}
	}

	@Test
	public void testReduce() {
		{
			// let result = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10].reduce(0) { $0 + $1 }
			Integer result = reduce(
					Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 0,
					new BiFunction<Integer, Integer, Integer>() {
						@Override
						public Integer apply(Integer t, Integer u) {
							return t + u;
						}
					});
			assertEquals(new Integer(55), result);
		}

		{
			Map<Character, Integer> characterToInteger = new HashMap<Character, Integer>();
			characterToInteger.put('A', 1);
			characterToInteger.put('B', 2);
			characterToInteger.put('C', 3);

			String result = reduce(
					characterToInteger,
					"",
					new BiFunction<String, Map.Entry<? extends Character, ? extends Integer>, String>() {
						@Override
						public String apply(
								String t,
								Map.Entry<? extends Character, ? extends Integer> u) {
							StringBuilder builder = new StringBuilder();
							for (int i = 0; i < u.getValue(); i++) {
								builder.append(u.getKey());
							}

							return t + builder.toString();
						}
					});
			assertEquals("ABBCCC", result);
		}
	}

	@Test
	public void testEnumerate() {
		Iterable<? extends Tuple2<Integer, ? extends String>> iterable = enumerate(Arrays
				.asList("abc", "def", "ghi"));
		Iterator<? extends Tuple2<Integer, ? extends String>> iterator = iterable
				.iterator();

		{ // 0
			assertTrue(iterator.hasNext());

			Tuple2<Integer, ? extends String> element = iterator.next();
			assertEquals(0, element.get0().intValue());
			assertEquals("abc", element.get1());
		}

		{ // 1
			assertTrue(iterator.hasNext());

			Tuple2<Integer, ? extends String> element = iterator.next();
			assertEquals(1, element.get0().intValue());
			assertEquals("def", element.get1());
		}

		{ // 2
			assertTrue(iterator.hasNext());

			Tuple2<Integer, ? extends String> element = iterator.next();
			assertEquals(2, element.get0().intValue());
			assertEquals("ghi", element.get1());
		}

		{ // no more element
			assertFalse(iterator.hasNext());

			try {
				iterator.next();
				fail("Must throw an exception.");
			} catch (NoSuchElementException e) {
			}
		}
	}

	@Test
	public void testJoin() {
		{
			// let result = ", ".join(["A", "B", "C"])
			String result = join(", ", Arrays.asList("A", "B", "C"));
			assertEquals("A, B, C", result);
		}

		{
			// let result = ", ".join([])
			String result = join(", ", Collections.emptyList());
			assertEquals("", result);
		}
	}

	@Test
	public void testPlus() {
		{
			// let result = [2, 3] + [5, 7, 11]
			List<Integer> result = plus(Arrays.asList(2, 3),
					Arrays.asList(5, 7, 11));
			assertEquals(Arrays.asList(2, 3, 5, 7, 11), result);
		}
	}

	@Test
	public void testAs() {
		{
			// let animal: Animal = Cat()
			// let cat = animal as Cat
			Animal animal = new Cat();
			Cat cat = as(animal, Cat.class);
			assertNotNull(cat);
		}

		{
			// let animal: Animal? = nil
			// let cat = animal as Cat
			Animal animal = null;
			try {
				@SuppressWarnings("unused")
				Cat cat = as(animal, Cat.class);
				fail();
			} catch (ClassCastException e) {
			}
		}
	}

	@Test
	public void testAsq() {
		{
			// let animal: Animal? = Cat()
			// let cat = animal as? Cat
			Animal animal = new Cat();
			Cat cat = asq(animal, Cat.class);
			assertNotNull(cat);
		}

		{
			// let animal: Animal? = nil
			// let cat = animal as? Cat
			Animal animal = null;
			Cat cat = asq(animal, Cat.class);
			assertNull(cat);
		}
	}

	public void testIs() {
		{
			// let animal: Animal? = Cat()
			// let result = animal is Cat
			Animal animal = new Cat();
			boolean result = is(animal, Cat.class);

			assertTrue(result);
			assertTrue(animal instanceof Cat);
		}

		{
			// let animal: Animal? = nil
			// let result = animal is Cat
			Animal animal = new Cat();
			boolean result = is(animal, Cat.class);

			assertFalse(result);
			assertTrue(animal instanceof Cat);
		}
	}

	@Test
	public void testQ() {
		{
			// let s: String? = "abc"
			// let l = s?.utf16Count
			String s = "abc";
			Integer l = q(s, new Function<String, Integer>() {
				@Override
				public Integer apply(String object) {
					return object.length();
				}
			});
			assertEquals(new Integer(3), l);
		}

		{
			// let s: String? = nil
			// let l = s?.utf16Count
			String s = null;
			Integer l = q(s, new Function<String, Integer>() {
				@Override
				public Integer apply(String object) {
					return object.length();
				}
			});
			assertNull(l);
		}

	}

	@Test
	public void testX() {
		{
			// let s: String? = "abc"
			// let t = s!
			String s = "abc";
			String t = x(s);
			assertEquals(t, "abc");
		}

		{
			// let s: String? = nil
			// let t = s!
			String s = null;
			try {
				@SuppressWarnings("unused")
				String t = x(s);
				fail();
			} catch (NullPointerException e) {
			}
		}
	}

	@Test
	public void testQq() {
		{
			// let s = "abc"
			// let r = s ?? "xyz"
			String s = "abc";
			String r = qq(s, "xyz");
			assertEquals("abc", r);
		}

		{
			// let s: String? = nil
			// let r = s ?? "xyz"
			String s = null;
			String r = qq(s, "xyz");
			assertEquals("xyz", r);
		}
	}

	@Test
	public void testArray() {
		{
			// let result = Array(count: 3, repeatedValue: 5)
			List<Integer> result = array(3, 5);
			assertEquals(Arrays.asList(5, 5, 5), result);
		}
	}

	private static class Animal {

	}

	private static class Cat extends Animal {

	}

	private static class SimpleEntry<K, V> implements Map.Entry<K, V> {
		private K key;
		private V value;

		public SimpleEntry(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int hashCode() {
			return (getKey() == null ? 0 : getKey().hashCode())
					^ (getValue() == null ? 0 : getValue().hashCode());
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Map.Entry)) {
				return false;
			}

			Map.Entry<K, V> e1 = this;
			Map.Entry<K, V> e2 = (Map.Entry<K, V>) obj;

			return (e1.getKey() == null ? e2.getKey() == null : e1.getKey()
					.equals(e2.getKey()))
					&& (e1.getValue() == null ? e2.getValue() == null : e1
							.getValue().equals(e2.getValue()));
		}
	}
}
