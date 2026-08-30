# Техническое задание: поиск студентов с использованием паттерна Компоновщик

## Задача

Реализовать механизм поиска студентов по диапазону значений с использованием паттерна **Компоновщик**.

Должны поддерживаться следующие критерии поиска:

- диапазон `groupNumber`;
- диапазон `averageGrade`;
- диапазон `recordBookNumber`.

Критерии должны поддерживать объединение в составной критерий.

Режим объединения критериев — **объединение (ИЛИ)** или **пересечение (И)** — задаётся в конструкторе основного класса.

---

# Названия классов и интерфейсов

## `StudentSearchCriteria`

Интерфейс отдельного критерия поиска.

### Контракт

```java
boolean matches(Student student);
```

Метод возвращает `true`, если студент соответствует критерию.

---

## `GroupNumberRangeCriteria`

Критерий поиска по диапазону `groupNumber`.

### Конструктор

```java
GroupNumberRangeCriteria(String min, String max);
```

### Метод

```java
boolean matches(Student student);
```

`groupNumber` имеет тип `String`, поэтому сравнение выполнять лексикографически.

Границы диапазона включаются.

---

## `AverageGradeRangeCriteria`

Критерий поиска по диапазону `averageGrade`.

### Конструктор

```java
AverageGradeRangeCriteria(double min, double max);
```

### Метод

```java
boolean matches(Student student);
```

Студент соответствует критерию, если:

```text
min <= averageGrade <= max
```

---

## `RecordBookNumberRangeCriteria`

Критерий поиска по диапазону `recordBookNumber`.

### Конструктор

```java
RecordBookNumberRangeCriteria(int min, int max);
```

### Метод

```java
boolean matches(Student student);
```

Студент соответствует критерию, если:

```text
min <= recordBookNumber <= max
```

---

# `CompositeStudentSearchCriteria`

Компоновщик критериев поиска.

Класс реализует:

```java
StudentSearchCriteria
```

и содержит другие реализации `StudentSearchCriteria`.

### Методы

```java
void add(StudentSearchCriteria criteria);
```

```java
void remove(StudentSearchCriteria criteria);
```

```java
boolean matches(Student student);
```

### Логика

`CompositeStudentSearchCriteria` должен поддерживать два режима:

```text
UNION       — объединение условий, ИЛИ
INTERSECTION — пересечение условий, И
```

Режим должен задаваться при создании компоновщика:

```java
CompositeStudentSearchCriteria(
    SearchOperation operation
);
```

где `SearchOperation` — перечисление:

```java
public enum SearchOperation {
    UNION,
    INTERSECTION
}
```

Для `UNION` студент подходит, если выполняется **хотя бы один** вложенный критерий.

Для `INTERSECTION` студент подходит, если выполняются **все** вложенные критерии.

---

# `StudentSearchService`

Основной класс поиска.

Он должен принимать интерфейс `StudentSearchCriteria`, а не конкретную реализацию критерия.

### Конструктор

```java
StudentSearchService(StudentSearchCriteria criteria);
```

### Метод

```java
MyList<Student> find(MyList<Student> students);
```

### Поведение

Метод `find()` должен:

1. Последовательно проверить студентов исходной коллекции.
2. Передать каждого студента в:

```java
criteria.matches(student)
```

1. Добавить подходящих студентов в новую `MyList<Student>`.
2. Вернуть новую коллекцию.

`StudentSearchService` не должен содержать логику конкретных диапазонов или объединения критериев.

### Обработка отсутствующего критерия

Если `StudentSearchService` создан без критерия:

```java
new StudentSearchService(null);
```

необходимо выбросить:

```java
IllegalArgumentException
```

Класс не должен выполнять поиск без заданного критерия.

---

# Пример использования

### Один критерий

```java
StudentSearchCriteria criteria =
        new AverageGradeRangeCriteria(4.0, 5.0);

StudentSearchService service =
        new StudentSearchService(criteria);

MyList<Student> result =
        service.find(students);
```

### Пересечение критериев

Например, студент должен одновременно:

- находиться в группе от `A10` до `C30`;
- иметь средний балл от `4.0` до `5.0`.

```java
CompositeStudentSearchCriteria criteria =
        new CompositeStudentSearchCriteria(
                SearchOperation.INTERSECTION
        );

criteria.add(
        new GroupNumberRangeCriteria("A10", "C30")
);

criteria.add(
        new AverageGradeRangeCriteria(4.0, 5.0)
);

StudentSearchService service =
        new StudentSearchService(criteria);
```

### Объединение критериев

Студент должен соответствовать хотя бы одному условию:

```java
CompositeStudentSearchCriteria criteria =
        new CompositeStudentSearchCriteria(
                SearchOperation.UNION
        );

criteria.add(
        new AverageGradeRangeCriteria(4.5, 5.0)
);

criteria.add(
        new RecordBookNumberRangeCriteria(10000, 20000)
);

StudentSearchService service =
        new StudentSearchService(criteria);
```

---

# Общие требования

1. `StudentSearchService` должен работать только через `StudentSearchCriteria`.
2. `StudentSearchService` не должен знать о конкретных критериях.
3. `CompositeStudentSearchCriteria` должен реализовывать тот же интерфейс `StudentSearchCriteria`.
4. Должна поддерживаться вложенность компоновщиков.

Например:

```text
Composite
 ├── GroupNumberRangeCriteria
 └── Composite
      ├── AverageGradeRangeCriteria
      └── RecordBookNumberRangeCriteria
```

1. Исходную `MyList<Student>` не изменять.
2. Результат возвращать в виде новой `MyList<Student>`.
3. Порядок найденных студентов должен сохраняться.
4. Стандартные `List`, `ArrayList`, `LinkedList` не использовать.
5. Готовые методы поиска не использовать.
6. При отсутствии совпадений возвращать пустую `MyList<Student>`.
7. Для диапазонов `min > max` выбрасывать `IllegalArgumentException`.

---

# Примеры тестов

## `GroupNumberRangeCriteria`

Проверить:

- значение внутри диапазона;
- значение равное нижней границе;
- значение равное верхней границе;
- значение вне диапазона.

## `AverageGradeRangeCriteria`

Проверить:

```text
3.9 → false
4.0 → true
4.5 → true
5.0 → true
5.1 → false
```

## `RecordBookNumberRangeCriteria`

Проверить значения внутри и вне диапазона, включая обе границы.

## Некорректный диапазон

Для каждого критерия проверить:

```text
min > max
```

Ожидается:

```java
IllegalArgumentException
```

## `INTERSECTION`

Создать два критерия и проверить:

```text
критерий A = true
критерий B = true
→ true
```

```text
критерий A = true
критерий B = false
→ false
```

## `UNION`

Проверить:

```text
A = true
B = false
→ true
```

```text
A = false
B = true
→ true
```

```text
A = false
B = false
→ false
```

## Компоновщик без критериев

Проверить поведение:

```java
new CompositeStudentSearchCriteria(
    SearchOperation.INTERSECTION
);
```

и:

```java
new CompositeStudentSearchCriteria(
    SearchOperation.UNION
);
```

Поведение должно быть явно определено и закреплено тестами.

Рекомендуется:

- пустое `INTERSECTION` → `true`;
- пустое `UNION` → `false`.

## `StudentSearchService`

Проверить:

- корректный поиск по одному критерию;
- пустой результат;
- пустую исходную коллекцию;
- сохранение порядка;
- неизменность исходной коллекции;
- работу с `CompositeStudentSearchCriteria`.

## Проверка вложенного Компоновщика

Создать компоновщик с вложенным компоновщиком и проверить корректность вычисления результата.

## Проверка отсутствующего критерия

Создать:

```java
new StudentSearchService(null);
```

Проверить `IllegalArgumentException`.

## Результат

Реализовать:

```text
StudentSearchCriteria.java
GroupNumberRangeCriteria.java
AverageGradeRangeCriteria.java
RecordBookNumberRangeCriteria.java
CompositeStudentSearchCriteria.java
SearchOperation.java
StudentSearchService.java
```

## Git

Название ветки:

```text
feature/composite-student-search
```
