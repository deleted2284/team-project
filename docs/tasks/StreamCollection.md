# Техническое задание: заполнение `MyList<Student>` посредством Stream API

## Задача

Реализовать механизм заполнения собственной коллекции `MyList<Student>` посредством Java Stream API без использования стандартных коллекций Java в качестве промежуточного или итогового хранилища.

## Требования

1. Итоговым результатом работы потока должна быть:

```java
MyList<Student>
```

1. Для сбора элементов потока непосредственно в `MyList<Student>` реализовать собственный `Collector`.

2. Стандартные коллекции Java (`List`, `ArrayList`, `LinkedList` и другие реализации `List`) запрещено использовать:

   - как итоговую коллекцию;
   - как промежуточное хранилище элементов перед их переносом в `MyList`.

3. `Student` должен создаваться только через уже существующий независимый класс `StudentBuilder`, если объект создаётся первоначально.

4. `MyListCollector` не должен создавать или изменять объекты `Student`. Он работает с уже созданными объектами.

5. При использовании `map` для получения нового `Student` использовать методы `with...()`, а не `StudentBuilder`.

6. Логика заполнения `MyList` должна быть отделена от логики класса `Student`.

7. Для добавления объектов использовать существующий метод:

```java
add(Student student)
```

1. Необходимо реализовать поддержку объединения частичных результатов, необходимую для работы `Collector`.

---

## `MyListCollector<T>`

Создать универсальный класс:

```java
public class MyListCollector<T>
        implements Collector<T, MyList<T>, MyList<T>> {
}
```

Реализовать методы:

```text
supplier()
accumulator()
combiner()
finisher()
characteristics()
```

Назначение:

- `supplier()` — создать пустой `MyList<T>`;
- `accumulator()` — добавить очередной элемент в `MyList<T>`;
- `combiner()` — объединить две частично заполненные коллекции;
- `finisher()` — вернуть готовый `MyList<T>`;
- `characteristics()` — определить характеристики сборщика.

Коллектор должен работать с `Student` без специальной логики, связанной с его полями.

---

## `MyCollectors`

Создать вспомогательный класс:

```java
public final class MyCollectors {

    public static <T> Collector<T, MyList<T>, MyList<T>> toMyList() {
        return new MyListCollector<>();
    }
}
```

Класс должен предоставлять удобный способ получения собственного сборщика.

---

## Контракт использования

Должна поддерживаться конструкция:

```java
MyList<Student> students = Stream.of(
        student1,
        student2,
        student3
).collect(MyCollectors.toMyList());
```

где `student1`, `student2`, `student3` — валидные объекты `Student`, предварительно созданные через `StudentBuilder`.

Результатом должен быть именно `MyList<Student>`.

---

## Пример `map`

`map` должен использоваться для преобразования одного `Student` в новый `Student`.

Исходный объект не изменяется. Для создания нового объекта использовать `with...()`.

Одно из преобразуемых полей должно быть `String` и формироваться по заданному бизнес-правилу:

```text
Заглавная латинская буква + двузначное число
```

Например:

```text
A07
B12
Z35
```

Пример:

```java
MyList<Student> students = Stream.of(
        student1,
        student2,
        student3
)
.map(student -> student.withGroupNumber(
        generateGroupNumber(student)
))
.collect(MyCollectors.toMyList());
```

Пример функции формирования значения:

```java
private static String generateGroupNumber(Student student) {
    int number = Math.abs(student.getRecordBookNumber().hashCode() % 100);
    char letter = (char) ('A'
            + Math.abs(student.getRecordBookNumber().hashCode()) % 26);

    return String.format("%c%02d", letter, number);
}
```

Результат `map` должен оставаться `Stream<Student>`, после чего он непосредственно собирается в `MyList<Student>`.

Формат создаваемого `groupNumber` должен соответствовать:

```text
^[A-Z][0-9]{2}$
```

---

## Пример `filter` совместно с `map`

Проверить совместную работу промежуточных операций:

```java
MyList<Student> students = Stream.of(
        student1,
        student2,
        student3
)
.filter(student -> student.getAverageGrade() >= 4.0)
.map(student -> student.withGroupNumber(
        generateGroupNumber(student)
))
.collect(MyCollectors.toMyList());
```

Проверить, что:

- в результат попали только студенты со средним баллом не ниже `4.0`;
- каждый результат является новым `Student`;
- `groupNumber` соответствует формату `[A-Z][0-9]{2}`;
- исходные объекты не изменились.

---

## Параллельные потоки

`combiner()` должен корректно объединять две частично сформированные `MyList<Student>`.

Реалистичный пример:

```java
MyList<Student> students = IntStream.range(0, 10_000)
        .parallel()
        .mapToObj(i -> student1.withGroupNumber(
                String.format("%c%02d",
                        'A' + (i % 26),
                        i % 100)
        ))
        .collect(MyCollectors.toMyList());
```

Проверить:

- результат содержит `10_000` объектов;
- ни один объект не потерян;
- количество объектов не превышает ожидаемого;
- каждый `groupNumber` соответствует формату `[A-Z][0-9]{2}`;
- объекты `Student` не изменяются после создания;
- `combiner()` корректно объединяет частичные результаты.

---

## Примеры тестов

### Заполнение несколькими `Student`

Создать три валидных объекта `Student` через `StudentBuilder` и собрать их:

```java
MyList<Student> result = Stream.of(
        student1,
        student2,
        student3
).collect(MyCollectors.toMyList());
```

Проверить:

```text
result != null
result.size() == 3
```

и соответствие элементов исходному потоку.

### Проверка порядка

Для:

```text
student1
student2
student3
```

проверить:

```text
result.get(0) == student1
result.get(1) == student2
result.get(2) == student3
```

### Пустой поток

```java
MyList<Student> result = Stream.<Student>empty()
        .collect(MyCollectors.toMyList());
```

Проверить:

```text
result != null
result.isEmpty()
```

### Проверка `map`

Преобразовать несколько `Student` через `withGroupNumber()`.

Проверить:

- исходные объекты не изменились;
- созданные объекты отличаются от исходных;
- `groupNumber` соответствует формату `[A-Z][0-9]{2}`.

### Проверка `filter` и `map`

Проверить, что после `filter` и `map` в `MyList<Student>` попадают только необходимые студенты с корректно сформированным `groupNumber`.

### Параллельный поток

Создать поток из `10_000` объектов через `parallel()`.

Проверить размер итоговой коллекции, отсутствие потери элементов и корректность `groupNumber`.

---

## Ограничения

Запрещено:

- использовать `Collectors.toList()`;
- использовать `ArrayList`, `LinkedList` или другие стандартные коллекции для накопления результата;
- сначала собирать `Student` в стандартную коллекцию, а затем переносить их в `MyList`;
- создавать `Student` внутри `MyListCollector`;
- изменять существующий `Student`;
- использовать `StudentBuilder` внутри `map` вместо `with...()`.

## Результат

Реализовать:

```text
MyListCollector.java
MyCollectors.java
```

Использовать существующие:

```text
Student.java
StudentBuilder.java
MyList.java
MyLinkedList.java
```

### Рекомендация по архитектуре

`MyListCollector` должен зависеть только от интерфейса `MyList<T>`, а не от конкретной реализации `MyLinkedList<T>`.

`MyCollectors` должен содержать только методы создания собственных сборщиков и не должен содержать бизнес-логику.
