# Техническое задание: сортировка слиянием

## Задача

Реализовать паттерн Strategy и собственный алгоритм сортировки слиянием объектов `Student`.

## Требования

1. Создать интерфейс `SortStrategy<T>` с методом:

```text
sort(MyList<T> list, Comparator<T> comparator)
```

1. Создать класс `MergeSortStrategy<T>`, реализующий `SortStrategy<T>`.

2. Реализовать алгоритм сортировки слиянием самостоятельно.

3. Готовые методы сортировки Java не использовать.

4. Сортировка должна работать с `MyList<T>`.

5. Сортировка должна использовать переданный `Comparator<T>`.

6. Реализовать сортировку `Student` по каждому из трёх полей:

   - `groupNumber`;
   - `averageGrade`;
   - `recordBookNumber`.

7. Создать отдельные компараторы:

   - `StudentGroupComparator`;
   - `StudentAverageGradeComparator`;
   - `StudentRecordBookComparator`.

8. `MergeSortStrategy` не должен зависеть от класса `Student`.

## Результат

```text
SortStrategy.java
MergeSortStrategy.java

StudentGroupComparator.java
StudentAverageGradeComparator.java
StudentRecordBookComparator.java
```
