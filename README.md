Swift2Java
==========

_Swift2Java_ is the library to emulate some functions and syntax in Swift for Java.

Usage
----------

```java
import static jp.co.qoncept.swift.Swift.*;
```

### map

#### Array

```swift
// Swift
let result = ["123", "456", "789"].map { $0.toInt()! }
```

```java
// Java
List<Integer> result = map(Arrays.asList("123", "456", "789"),
    new Function<String, Integer>() {
        @Override
        public Integer apply(String t) {
            return Integer.parseInt(t);
        }
    });
```

#### Optional

```swift
// Swift
let a: Int? = 3
let result = a.map { $0 * $0 }
```

```java
// Java
Integer a = 3;
Integer result = map(a, new Function<Integer, Integer>() {
    @Override
    public Integer apply(Integer t) {
        return t * t;
    }
});
```

### flatMap

#### Array

```swift
// Swift
let result: [Int] = [[2], [3, 5], [7, 11, 13]].flatMap { $0 }
```

```java
// Java
List<Integer> result = flatMap(
        Arrays.asList(Arrays.asList(2), Arrays.asList(3, 5),
                Arrays.asList(7, 11, 13)),
        new Function<List<Integer>, List<Integer>>() {
            @Override
            public List<Integer> apply(List<Integer> t) {
                return t;
            }
        });
```

#### Optional

```swift
// Swift
let a: Int? = 2
let b: Int? = 3
let result: Int? = a.flatMap { a0 in b.flatMap { b0 in a0 + b0 } }
```

```java
// Java
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
```

### filter

```swift
// Swift
let result = [123, 456, 789].filter { $0 % 2 == 1 }
```

```java
// Java
List<Integer> result = filter(Arrays.asList(123, 456, 789),
    new Predicate<Integer>() {
        @Override
        public boolean test(Integer t) {
            return t % 2 == 1;
        }
    });
```

### reduce

```swift
// Swift
let result = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10].reduce(0) { $0 + $1 }
```

```java
// Java
Integer result = reduce(
    Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 0,
    new BiFunction<Integer, Integer, Integer>() {
        @Override
        public Integer apply(Integer t, Integer u) {
            return t + u;
        }
    });
```

### enumerate

```swift
// Swift
for (index, element) in enumerate(["abc", "def", "ghi"]) { 
    println("\(index): \(element)")
} 
```

```java
// Java
for (Tuple2<Integer, ? extends String> indexAndElement : enumerate(Arrays
        .asList("abc", "def", "ghi"))) {
    System.out.println(indexAndElement.get0() + ": " + indexAndElement.get1());
}
```

### join

```swift
// Swift
let result = ", ".join(["A", "B", "C"])
```

```java
// Java
String result = join(", ", Arrays.asList("A", "B", "C"));
```

### +

```swift
// Swift
let result = [2, 3] + [5, 7, 11]
```

```java
// Java
List<Integer> result = plus(Arrays.asList(2, 3), Arrays.asList(5, 7, 11));
```

### as

#### (A)

```swift
// Swift
let animal: Animal = Cat()
let cat = animal as Cat
```

```java
// Java
Animal animal = new Cat();
Cat cat = as(animal, Cat.class); // the created Cat object
```

#### (B)

```swift
// Swift
let animal: Animal? = nil
let cat = animal as Cat
```

```java
// Java
Animal animal = null;
Cat cat = as(animal, Cat.class); // ClassCastException
```

### as?

#### (A)

```swift
// Swift
let animal: Animal? = Cat()
let cat = animal as? Cat

```

```java
// Java
Animal animal = new Cat();
Cat cat = asq(animal, Cat.class); // the created Cat object
```

#### (B)

```swift
// Swift
let animal: Animal? = nil
let cat = animal as? Cat
```

```java
// Java
Animal animal = null;
Cat cat = asq(animal, Cat.class); // null
```

### is

#### (A)

```swift
// Swift
let animal: Animal? = Cat()
let result = animal is Cat
```

```java
// Java
Animal animal = new Cat();
boolean result = is(animal, Cat.class); // true
```

#### (B)

```swift
// Swift
let animal: Animal? = nil
let result = animal is Cat
```

```java
// Java
Animal animal = new Cat();
boolean result = is(animal, Cat.class); // false
```

### ? (Optional Chain)

#### (A)

```swift
// Swift
let s: String? = "abc"
let l = s?.utf16Count
```

```java
// Java
String s = "abc";
Integer l = q(s, new Function<String, Integer>() {
    @Override
    public Integer apply(String object) {
        return object.length();
    }
}); // 3
```

#### (B)

```swift
// Swift
let s: String? = nil
let l = s?.utf16Count
```

```java
// Java
String s = null;
Integer l = q(s, new Function<String, Integer>() {
    @Override
    public Integer apply(String object) {
        return object.length();
    }
}); // null
```

### ! (Forced Unwrapping)

#### (A)

```swift
// Swift
let s: String? = "abc"
let t = s!
```

```java
// Java
String s = "abc";
String t = x(s);
```

#### (B)

```swift
// Swift
let s: String? = nil
let t = s!
```

```java
// Java
String s = null;
String t = x(s); // NullPointerException
```

### ??

#### (A)

```swift
// Swift
let s = "abc"
let r = s ?? "xyz"

```

```java
// Java
String s = "abc";
String r = qq(s, "xyz"); // "abc"
```

#### (B)

```swift
// Swift
let s: String? = nil
let r = s ?? "xyz"
```

```java
// Java
String s = null;
String r = qq(s, "xyz"); // "xyz"
```

### Array (initializer)

```swift
// Swift
let result = Array(count: 3, repeatedValue: 5)
```

```java
// Java
List<Integer> result = array(3, 5);
```

License
----------

MIT License
