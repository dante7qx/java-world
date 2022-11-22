## Java 类型

### 一. Type
Type作为Java编程语言中所有类型的通用超级接口，涵盖Class（原始类型）、ParameterizedType（参数化的类型、泛型类型）、TypeVariable（类型变量）、WildcardType（通配符类型）、GenericArrayType（数组类型），以Class为切入点将所有的类型串联起来了，方便我们通过反射在运行时解析复杂的数据类型。

```java
public interface Type {
    default String getTypeName() {
        return toString();
    }
}
```

### 二. AnnotatedElement

AnnotatedElement代表了JVM中当前正在运行的程序中带注解的元素 ， 该接口允许以反射方式读取注解。该接口中所有方法返回的所有注解都是不可变的和可序列化的。调用方可以修改此接口的方法返回的数组，而不会影响返回给其他调用方的数组。 但是注解是可以以不同的形式存在于元素上的，例如直接存在于元素上，或者通过`@Repeatable`以另一个容器注解的形式存在于元素上，甚至是通过`@Inherited`以继承的方式存在于元素的父类上。总之要获取一个元素上的注解，不能单纯的以**获取元素上的注解**这种模糊的概念去获取，而应该以更为明确的**获取以何种方式存在于元素上的注解**获取相应的注解。

- 注解存在形式

  1. **直接存在**
     某个注解直接标注在元素上
  2. **间接存在**
     被`@Repeatable`注解标注的注解注释在元素上时，该注解就是间接存在于元素上的
  3. **存在**
     某个**元素上直接存在**某个注解或者只要在该元素代表类的所有**父类中找到一个直接存在**该注解的父类，则代表该注解存在于元素之上
  4. **关联的**
     **元素上直接存在或者间接存在**某个注解或者只要在该元素代表类的所有**父类中找到一个直接存在或者间接存在**该注解的父类，则代表元素与该注解是关联的

- 获取元素注解

  |              |                                                     | 注解存在的形式 |          |      |        |
  | ------------ | --------------------------------------------------- | -------------- | -------- | ---- | ------ |
  | 方法         |                                                     | 直接存在       | 间接存在 | 存在 | 关联的 |
  | T            | getAnnotation(Class annotationClass)                |                |          | √    |        |
  | Annotation[] | getAnnotations()                                    |                |          | √    |        |
  | T[]          | getAnnotationsByType(Class annotationClass)         |                |          |      | √      |
  | T            | getDeclaredAnnotation(Class annotationClass)        | √              |          |      |        |
  | Annotation[] | getDeclaredAnnotations()                            | √              |          |      |        |
  | T[]          | getDeclaredAnnotationsByType(Class annotationClass) | √              | √        |      |        |

### 三. AnnotatedType
AnnotatedType表示当前程序中可能被注解的类型。 这些类型可能是Java编程语言中的任何Type，包括Class（原始类型）、ParameterizedType（参数化的类型、泛型类型）、TypeVariable（类型变量）、WildcardType（通配符类型）、GenericArrayType（泛型数组类型）这五种java中的具体类型。而AnnotatedType对这些类型作了一层包装，因为这些类型都是可能被注解的。同时AnnotatedType继承了AnnotatedElement使得我们能够在运行时基于反射api去分析获取那些十分复杂的数据结构中的注解。


``` java
public interface AnnotatedType extends AnnotatedElement {
    public Type getType();
}
```

### 四. 参考：
- https://www.zyc.red/Java/Reflection/Type/
- https://www.zyc.red/Java/Reflection/AnnotatedElement/
- https://www.zyc.red/Java/Reflection/AnnotatedType/
