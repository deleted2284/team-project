# Дополнение к техническому заданию: изменение типа `recordBookNumber`

Данное дополнение имеет **приоритет над основным ТЗ `DataSource.md`**.

## Изменение модели `Student`

Поле:

```java
private final String recordBookNumber;
```

заменено на:

```java
private final int recordBookNumber;
```

Метод:

```java
getRecordBookNumber()
```

возвращает `int`.

## Требования к `DataSource`

Во всех реализациях источников данных необходимо учитывать новый тип `recordBookNumber`.

### `ManualDataSource`

`recordBookNumber` должен передаваться как `int`:

```java
setStudent(
        int index,
        String groupNumber,
        double averageGrade,
        int recordBookNumber
);
```

Строковое значение номера зачётной книжки больше не используется.

### `RandomDataSource`

Для `recordBookNumber` необходимо задавать и генерировать **числовой диапазон**:

```text
минимальное значение — int
максимальное значение — int
```

Генерируемое значение должно быть целым числом и соответствовать ограничениям `Student`.

### `FileDataSource`

В файле `recordBookNumber` должен записываться как целое число.

Формат записи:

```text
groupNumber;averageGrade;recordBookNumber
```

Пример:

```text
A12;4.75;12345
B32;3.80;12346
C17;4.20;12347
```

При чтении значение необходимо преобразовать в `int` до создания `Student`.

## Приоритет

При противоречии между данным дополнением и основным `DataSource.md` использовать требования этого дополнения.

Все остальные требования основного технического задания остаются без изменений.
