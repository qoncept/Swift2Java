Swift2Java
==========

_Swift2Java_ is the library to emulate some syntax in Swift for Java.

Usage
----------

```java
import static jp.co.qoncept.swift.Swift.*;
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
let animal: Animal = nil
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

### ? (Optional Chain)

#### (A)

```swift
// Swift
let s: String? = "abc"
let l = s?.length
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
let l = s?.length
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
let s = "abc"
let r = s ?? "xyz"
```

```java
// Java
String s = "abc";
String r = qq(s, "xyz");
```

#### (B)

```swift
// Swift
let s: String? = nil
let l = s!
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

License
----------

MIT License
