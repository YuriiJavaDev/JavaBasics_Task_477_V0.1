## Objects class: methods equals, hashCode, hash.

### 1. Introduction to the Objects class

Let's get to the point! If you're tired of manually checking values for null and writing hash calculations for each field, the java.util.Objects utility class will come to your rescue. Its purpose is to make working with objects simpler, more concise, and safer.

It's truly a "Swiss knife": the class can safely compare objects for equality (without the risk of a NullPointerException), conveniently calculate hash codes, compare using a comparator, and check arguments for null.

#### Objects.equals: Safe Comparison with Null-Awareness

If you simply write a.equals(b), and a is null, you'll get a NullPointerException. Manual checks are cumbersome. Objects.equals(a, b) does it all for you:

- If both are null, it returns true.
- If exactly one is null, it returns false.
- If both are not null, it calls the regular equals function.

```java
import java.util.Objects;

    String a = null;
    String b = "Java";
    System.out.println(Objects.equals(a, b)); // false
    
    String c = null;
    System.out.println(Objects.equals(a, c)); // true
    
    String d = "Java";
    String e = "Java";
    System.out.println(Objects.equals(d, e)); // true
```

**Why is this convenient?** The code is shorter, cleaner, and protected from accidental NPEs.

### 2. Objects.hash and hashCode: Concise Hash Calculation

Overriding hashCode along with equals is easy to make a mistake, especially when there are many fields. Hand-written code often looks cumbersome and fragile:

```java
@Override
public int hashCode() {
    int result = 17;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + age;
    return result;
}
```

The Objects.hash method solves the problem—succinctly, safely, and with null support:

```java
import java.util.Objects;

public class Person {
    private String name;
    private int age;
    
    // ... constructor, getters, etc.
    
    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
```

**Important point:** Objects.hash uses varargs and creates an array—in rare, high-load situations, a manual hashCode can be faster. For most applications, the difference is insignificant.

### 3. Objects.compare: Delegate Comparison to a Comparator

Sometimes you need to compare two objects using a pre-prepared Comparator. Instead of directly calling comparator.compare(a, b), you can use:

```java
int result = Objects.compare(a, b, comparator);
```

This method:

- Returns 0 if the objects are equal.
- Considers null to be "less than" any non-null object.
- Otherwise, delegates the logic to the passed comparator.

```java
import java.util.Comparator;
import java.util.Objects;

class Person {
    private String name;
    Person(String name) { 
        this.name = name; 
    } 
    public String getName() { 
        return name; 
    }
}

public class Main { 
    public static void main(String[] args) { 
        Person a = new Person("Anna"); 
        Person b = new Person("Boris"); 
        Comparator<Person> byName = Comparator.comparing(Person::getName); 
        
        System.out.println(Objects.compare(a, b, byName)); // <0, because "Anna" < "Boris" 
        System.out.println(Objects.compare(a, null, byName)); // >0, because a != null 
        System.out.println(Objects.compare(null, b, byName)); // <0, because null < b
        System.out.println(Objects.compare(null, null, byName)); // 0
    }
}
```

- Explanation

This line:
`Comparator<Person> byName = Comparator.comparing(Person::getName);`
creates a byName comparator object.
And then it's passed here:

`Objects.compare(a, b, byName)`
That is:
a — the first object
b — the second object
byName — the comparison logic

### 4. Objects.requireNonNull: insurance against "invisible" errors

If a method should only accept non-null values, check for this immediately. Objects.requireNonNull will throw a NullPointerException with your message:

```java
public void setName(String name) {
    this.name = Objects.requireNonNull(name, "Name cannot be null");
}
```

- Explanation

**The idea is this:**
It's better to catch an error when passing an invalid value than to later figure out where the program crashed.
**Without checking:**

```java
    String name = null;
    System.out.println(name.length());
```

We get: **NullPointerException**

But the problem is that the error occurs later, and it's not always clear where the **null** came from.

With **requireNonNull**:

```java
    String name = null;
    Objects.requireNonNull(name, "Name must not be null");
    System.out.println(name.length());
```

The error appears immediately:
**NullPointerException: Name must not be null**

How the method works:
`Objects.requireNonNull(obj, "message")`

Logically, this is almost the same as:

```java
    if (obj == null) {
        throw new NullPointerException("message");
    }
```

Main benefit:

1. The error occurs immediately. Not after 20 lines of code.
2. Clear message
   Instead of: **NullPointerException**
   we get: **User must not be null**
3. Often used in constructors
   For example:

```java
class Person {
    private String name;
    Person(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }
}
```

Now you can't create an object with null:
new Person(null);
↓
NullPointerException: name cannot be null

### 5. Example: Correct implementation of equals, hashCode, and compareTo with Objects

```java
import java.util.Objects;

public class Person implements Comparable<Person> {
    private String name;
    private int age;
    
    public Person(String name, int age) { 
        this.name = Objects.requireNonNull(name, "Name cannot be null"); 
        this.age = age; 
    } 
    
    public String getName() {
        return name;
    } 
    public int getAge() {
        return age;
    } 
    
    @Override 
    public boolean equals(Object o) { 
        if (this == o) return true; // Comparison by reference 
        if (o == null || getClass() != o.getClass()) return false; 
        Person person = (Person) o; 
        // Safe comparison with null 
        return age == person.age && Objects.equals(name, person.name); 
    } 
    
    @Override 
    public int hashCode() { 
        return Objects.hash(name, age); // Concise and safe
    }
    
    @Override
    public int compareTo(Person other) {
        // First compare by name, then by age
        int cmp = name.compareTo(other.name);
        if (cmp != 0) return cmp;
        return Integer.compare(age, other.age);
    }
}
```

Now you can store objects in a HashSet, use them as keys in a HashMap, compare them by equality, and sort lists (for example, using Collections.sort).

### 6. Real-World Applications: Shortening Code and Reducing Errors

#### Example: List of Users in an Application

Thanks to the correct equals/hashCode pair, searching in collections works predictably:

```java
import java.util.ArrayList;
import java.util.List;

    List<Person> users = new ArrayList<>();
    users.add(new Person("Anna", 25));
    users.add(new Person("Boris", 30));
    
    Person search = new Person("Anna", 25);
    System.out.println(users.contains(search)); // true
```

#### Example: Working with Nullable Fields

If a class has fields that can be null (for example, middle name), use Objects.equals and Objects.hash:

```java
import java.util.Objects;

public class User {
    private String firstName;
    private String middleName; // Can be null
    private String lastName;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) 
        && Objects.equals(middleName, user.middleName) 
        && Objects.equals(lastName, user.lastName); 
    } 
    
    @Override 
    public int hashCode() { 
        return Objects.hash(firstName, middleName, lastName); 
    }
}
```

### 7. Table: Main Methods of the Objects Class

| **Method** | **Purpose** | **Usage Example** |
| --- | --- | --- |
| `Objects.equals(a, b)` | Safely compare two objects, taking null into account | `Objects.equals(a, b)` |
| `Objects.hash(a, b, ...)` | Concise hash code calculation over multiple fields | `Objects.hash(name, age)` |
| `Objects.compare(a, b, comparator)` | Comparison via comparator, null-safe | `Objects.compare(p1, p2, byNameComparator)` |
| `Objects.requireNonNull(obj[, msg])` | Check for null, throws NullPointerException | `Objects.requireNonNull(name, "Name cannot be null")` |
| `Objects.isNull(obj) / Objects.nonNull(obj)` | Null/not null check (useful in the Stream API) | `list.stream().filter(Objects::nonNull)` |

### 8. Common Mistakes When Using Objects Class Methods

**Mistake №1:** Forgot to use Objects.equals **for nullable fields.** Comparing fields directly using equals can result in a NullPointerException. Use Objects.equals(middleName, other.middleName).

**Mistake №2:** Not all fields are included in the hashCode. Fields included in equals must also be included in the hashCode, otherwise the behavior of the HashSet/HashMap will become unpredictable.

**Mistake №3:** Manually defining hashCode **with an error.** Maintaining coefficients and null checks is difficult. Objects.hash does this for you; use it unless you have strict performance requirements.

**Mistake №4:** Not using Objects.requireNonNull where it's the class contract. If a field isn't nullable, check it in the constructor/setter—the error will show up right away, not deep in the call stack.

**Mistake №5:** Using Objects.hash **for arrays.** Arrays require Arrays.hashCode, and nested arrays require Arrays.deepHashCode. Similarly, Arrays.equals/Arrays.deepEquals are available for comparing contents.
