# Техническое задание: обновление `Student`

## Задача

Обновить класс `Student` и связанный с ним код в соответствии с новой моделью данных и требованиями к неизменяемости объекта.

Текущая реализация содержит:

```java
private final String groupNumber;
private final double averageGrade;
private final String recordBookNumber;
```

Необходимо изменить тип `recordBookNumber` и добавить методы создания изменённых копий `Student`.

## Изменения в `Student`

### 1. Изменить тип `recordBookNumber`

Поле:

```java
private final String recordBookNumber;
```

заменить на:

```java
private final int recordBookNumber;
```

Метод:

```java
getRecordBookNumber()
```

должен возвращать `int`.

Все проверки, `equals()`, `hashCode()` и `toString()` должны быть адаптированы под новый тип.

### 2. Добавить бизнес-правило для `groupNumber`

`groupNumber` должен соответствовать строго следующему формату:

```text
<Заглавная буква латинского алфавита><2 цифры>
```

Регулярное выражение:

```text
^[A-Z][0-9]{2}$
```

Допустимые значения:

```text
A12
B32
Z00
```

Недопустимые значения:

```text
а12
a12
A1
A123
AA1
а1А
аа!!а123
23фи!12
№№###*()
```

Проверка должна выполняться при создании `Student`.

`Student` не может существовать с некорректным `groupNumber`.

### 3. Проверить `averageGrade`

Сохранить правило:

```text
0.0 <= averageGrade <= 5.0
```

Значения `0.0` и `5.0` допустимы.

### 4. Проверить `recordBookNumber`

После преобразования в `int` установить бизнес-правило:

```text
recordBookNumber > 0
```

Значение `0` и отрицательные значения считаются недопустимыми.

---

# Методы `with...()`

Добавить методы, возвращающие **новый объект `Student`**, не изменяя текущий объект.

Необходимо реализовать:

```java
withGroupNumber(String groupNumber)
withAverageGrade(double averageGrade)
withRecordBookNumber(int recordBookNumber)
```

Каждый метод должен:

1. Сохранять исходный объект без изменений.
2. Изменять только указанное поле в новом объекте.
3. Сохранять остальные поля исходного объекта.
4. Повторно применять все правила валидации.
5. Возвращать новый валидный объект `Student`.

Пример:

```java
Student updated = student.withAverageGrade(4.8);
```

После выполнения:

```text
student.getAverageGrade() == прежнее значение
updated.getAverageGrade() == 4.8
```

Если новое значение невалидно, метод должен выбросить исключение, а исходный объект не должен измениться.

---

# `StudentBuilder`

Обновить существующий `StudentBuilder` в соответствии с новой моделью `Student`.

Методы должны соответствовать типам:

```java
setGroupNumber(String groupNumber)
setAverageGrade(double averageGrade)
setRecordBookNumber(int recordBookNumber)
```

`build()` должен создавать только валидный `Student`.

Все новые правила валидации должны применяться при создании объекта через `StudentBuilder`.

---

# Обновление зависимого кода

Найти и обновить весь код, использующий `Student` и изменяемые методы или типы.

В частности, обновить:

- `Comparator<Student>`;
- сортировки;
- источники данных;
- тестовые классы;
- создание `Student`;
- `with...()`-операции;
- чтение и запись данных, если они используют `recordBookNumber`.

Например, существующий:

```java
implements Comparator<Student>
```

должен использовать актуальный метод:

```java
getAverageGrade()
```

вместо ошибочного `getAvarageGrade()`.

Компаратор `recordBookNumber` должен сравнивать значения как числа.

Например:

```java
Integer.compare(
        s1.getRecordBookNumber(),
        s2.getRecordBookNumber()
)
```

Все места, где `recordBookNumber` рассматривался как `String`, необходимо адаптировать к `int`.

---

# Тесты

Обновить существующий `StudentTest` с учётом новой модели.

## Создание корректного `Student`

Проверить создание объекта со значениями:

```text
groupNumber = A12
averageGrade = 4.5
recordBookNumber = 12345
```

Проверить корректность всех геттеров.

## Проверка `groupNumber`

Проверить успешное создание для:

```text
A12
B32
Z00
```

Проверить исключение для:

```text
a12
A1
A123
AA1
а1А
аа!!а123
23фи!12
№№###*()
```

## Проверка `averageGrade`

Проверить:

```text
0.0 — допустимо
5.0 — допустимо
-1.0 — недопустимо
6.0 — недопустимо
```

## Проверка `recordBookNumber`

Проверить:

```text
1 — допустимо
12345 — допустимо
0 — недопустимо
-1 — недопустимо
```

## Тесты `with...()`

### `withGroupNumber()`

Проверить, что:

- возвращается новый объект;
- `groupNumber` изменён;
- `averageGrade` не изменился;
- `recordBookNumber` не изменился;
- исходный `Student` остался прежним.

### `withAverageGrade()`

Проверить те же свойства для `averageGrade`.

### `withRecordBookNumber()`

Проверить те же свойства для `recordBookNumber`.

### `with...()` с невалидным значением

Для каждого метода передать некорректное значение.

Проверить:

- выбрасывается `IllegalArgumentException`;
- исходный объект не изменяется;
- не создаётся невалидный `Student`.

## `equals()` и `hashCode()`

Обновить существующие тесты с учётом `int recordBookNumber`.

Проверить:

- одинаковые студенты равны;
- одинаковые студенты имеют одинаковый `hashCode`;
- изменение любого поля создаёт отличающийся объект.

## Результат

Необходимо обновить:

```text
Student.java
StudentBuilder.java
StudentTest.java
```

а также **все классы проекта, зависящие от изменённого интерфейса `Student`**, включая компараторы и другие реализации, использующие `recordBookNumber`, `groupNumber`, `averageGrade` или их геттеры.

### Основной принцип

`Student` должен оставаться единственной точкой истины для валидности данных:

```text
StudentBuilder ──┐
                 ├──> Student
with...() ───────┘
```

Ни `Builder`, ни `with...()`, ни внешние классы не должны позволять создать невалидный объект.
