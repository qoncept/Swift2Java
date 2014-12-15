package jp.co.qoncept.swift;

import static jp.co.qoncept.swift.Swift.as;
import static jp.co.qoncept.swift.Swift.asq;
import static jp.co.qoncept.swift.Swift.q;
import static jp.co.qoncept.swift.Swift.qq;
import static jp.co.qoncept.swift.Swift.x;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import jp.co.qoncept.functional.Function;

import org.junit.Test;

public class SwiftTest {
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
			// let animal: Animal = nil
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

	@Test
	public void testQ() {
		{
			// let s: String? = "abc"
			// let l = s?.length
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
			// let l = s?.length
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
			// let l = s!
			String s = "abc";
			String t = x(s);
			assertEquals(t, "abc");
		}

		{
			// let s: String? = nil
			// let l = s!
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

	private static class Animal {

	}

	private static class Cat extends Animal {

	}
}
