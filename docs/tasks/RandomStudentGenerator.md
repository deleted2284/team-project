# Техническое задание: генератор случайных студентов

## Задача

Реализовать класс для формирования `MyList<Student>` со случайными значениями.

По умолчанию ограничения диапазона для генерации не задаются. В таком случае значения должны генерироваться во всём допустимом диапазоне, установленном правилами класса `Student`.

Генерация объектов должна выполняться через существующий `StudentBuilder`.

## Название класса

```text id="m9s0q1"
RandomStudentGenerator
```

## Контракт класса

Класс должен хранить текущие ограничения генерации для каждого поля `Student`.

Для каждого поля необходимо предоставить по два метода: левое и правое ограничение.

### `groupNumber`

Тип поля: `String`.

```java id="t2z8k4"
RandomStudentGenerator setMinGroupNumber(String minGroupNumber);
```

```java id="q7x5n1"
RandomStudentGenerator setMaxGroupNumber(String maxGroupNumber);
```

Ограничения применяются при генерации `groupNumber`.

Сравнение границ выполнять в соответствии с правилами, используемыми для `groupNumber` в `Student`.

### `averageGrade`

Тип поля: `double`.

```java id="v4n2j8"
RandomStudentGenerator setMinAverageGrade(double minAverageGrade);
```

```java id="r5c1p9"
RandomStudentGenerator setMaxAverageGrade(double maxAverageGrade);
```

Диапазон должен быть включительным:

```text id="u3k7w2"
minAverageGrade <= averageGrade <= maxAverageGrade
```

### `recordBookNumber`

Тип поля: `int`.

```java id="d8m4x6"
RandomStudentGenerator setMinRecordBookNumber(int minRecordBookNumber);
```

```java id="e1q9s5"
RandomStudentGenerator setMaxRecordBookNumber(int maxRecordBookNumber);
```

Диапазон должен быть включительным:

```text id="y6p2v8"
minRecordBookNumber <= recordBookNumber <= maxRecordBookNumber
```

## Метод генерации

Основной метод:

```java id="k3w7m1"
MyList<Student> generate(int size);
```

Контракт:

- создаёт ровно `size` объектов `Student`;
- все объекты должны быть валидными;
- каждый объект создаётся через `StudentBuilder`;
- результат возвращается как `MyList<Student>`;
- стандартные `List`, `ArrayList`, `LinkedList` и другие контейнеры использовать нельзя.

## Поведение диапазонов по умолчанию

Если для поля ограничение не задано, используется весь допустимый диапазон этого поля.

Необходимо учитывать существующие ограничения `Student`, в частности:

```text id="z4r8t2"
averageGrade:
getMinAverageGrade()
getMaxAverageGrade()

recordBookNumber:
getMinRecordBookNumber()
```

Для `groupNumber` использовать существующий шаблон:

```text id="n5c7y3"
getGroupNumberPattern()
```

Генерируемое значение `groupNumber` должно соответствовать требованиям `Student`.

## Проверка ограничений

При установке границ необходимо проверять их корректность.

Недопустимые случаи:

```text id="h2k6p4"
min > max
```

Для них выбрасывать:

```java id="c7v3n9"
IllegalArgumentException
```

Ограничение также не должно выходить за допустимые значения `Student`.

## Рекомендуемая реализация

Класс должен хранить шесть ограничений:

```text id="b5q9x2"
minGroupNumber
maxGroupNumber

minAverageGrade
maxAverageGrade

minRecordBookNumber
maxRecordBookNumber
```

Для каждого поля значения генерируются независимо.

Рекомендуется использовать стандартный генератор случайных чисел Java, например:

```java id="a8f4k1"
Random
```

или:

```java id="p3m7d5"
ThreadLocalRandom
```

При создании `Student`:

```text id="w6j2r9"
сгенерировать поля
        ↓
StudentBuilder
        ↓
build()
        ↓
Student
        ↓
MyList<Student>
```

Дополнительную валидацию, дублирующую правила `Student`, в генераторе не реализовывать.

## Примеры использования

Без настройки диапазонов:

```java id="s2k8m4"
RandomStudentGenerator generator =
        new RandomStudentGenerator();

MyList<Student> students =
        generator.generate(100);
```

С настройкой диапазонов:

```java id="f7q3n1"
RandomStudentGenerator generator =
        new RandomStudentGenerator()
                .setMinGroupNumber("A00")
                .setMaxGroupNumber("Z99")
                .setMinAverageGrade(3.0)
                .setMaxAverageGrade(5.0)
                .setMinRecordBookNumber(10000)
                .setMaxRecordBookNumber(99999);

MyList<Student> students =
        generator.generate(100);
```

## Тесты

### Генерация без ограничений

```java id="r1w5p8"
MyList<Student> result = generator.generate(100);
```

Проверить:

- размер равен `100`;
- все элементы не равны `null`;
- каждый `Student` валиден.

### Ограничение `averageGrade`

Задать:

```text id="x4n7k2"
3.0 — 4.0
```

Проверить, что все значения находятся в указанном диапазоне.

### Ограничение `recordBookNumber`

Задать:

```text id="q9m3v6"
10000 — 20000
```

Проверить диапазон и включение границ.

### Ограничение `groupNumber`

Задать левую и правую границы.

Проверить, что все сгенерированные значения находятся между ними согласно правилам сравнения `groupNumber`.

### Граничные значения

Установить:

```text id="p8c4s1"
min == max
```

Проверить, что все сгенерированные значения равны указанной границе.

### Некорректный диапазон

Проверить выброс `IllegalArgumentException`, если:

```text id="z6t2h5"
min > max
```

### Некорректное ограничение

Передать диапазон, выходящий за допустимые значения `Student`.

Проверить выброс исключения.

### Генерация пустой коллекции

Для:

```java id="j5r8w3"
generate(0)
```

ожидается пустой `MyList<Student>`.

### Некорректный размер

Для отрицательного размера:

```java id="n2k7p4"
generate(-1)
```

ожидается `IllegalArgumentException`.

## Результат

Необходимо реализовать:

```text id="v8m3q6"
RandomStudentGenerator.java
```

Класс должен использовать существующие:

```text id="f1j9x4"
Student.java
StudentBuilder.java
MyList.java
```

Изменять интерфейсы `Student`, `StudentBuilder` и `MyList` для выполнения данного задания не требуется.
