# Техническое задание: сортировка

## Задача

Реализовать паттерн Strategy и собственный алгоритм сортировки объектов `Student`.

## Требования

1. Создать интерфейс `SortStrategy<T>` с методом:

```text
sort(MyList<T> list, Comparator<T> comparator)
```

1. Создать класс `BubbleSortStrategy<T>`, реализующий `SortStrategy<T>`.

2. Алгоритм сортировки реализовать самостоятельно. Готовые методы сортировки Java не использовать.

3. Сортировка должна работать с `MyList<T>`, а не со стандартными коллекциями Java.

4. Сортировка должна использовать переданный `Comparator<T>`.

5. Реализовать возможность сортировки `Student` по каждому из трёх полей:

   - `groupNumber`;
   - `averageGrade`;
   - `recordBookNumber`.

6. Для каждого поля создать отдельный компаратор:

   - `StudentGroupComparator`;
   - `StudentAverageGradeComparator`;
   - `StudentRecordBookComparator`.

7. Алгоритм сортировки не должен зависеть от класса `Student`.

## Результат

Должны быть реализованы:

```text
SortStrategy.java
BubbleSortStrategy.java

StudentGroupComparator.java
StudentAverageGradeComparator.java
StudentRecordBookComparator.java
```

Классы должны работать с `MyList` и не использовать готовые реализации сортировки.
