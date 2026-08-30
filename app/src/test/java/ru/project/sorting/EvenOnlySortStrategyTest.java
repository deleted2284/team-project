package ru.project.sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import org.junit.jupiter.api.Test;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;

class EvenOnlySortStrategyTest {

  @Test
  void shouldHandleEmptyList() {
    MyList<Integer> list = new MyLinkedList<>();

    EvenOnlySortStrategy<Integer> strategy =
        new EvenOnlySortStrategy<>(new MergeSortStrategy<>(), value -> value);

    strategy.sort(list, Integer::compare);

    assertEquals(0, list.size());
  }

  @Test
  void shouldKeepSingleEvenElementUnchanged() {
    MyList<Integer> list = new MyLinkedList<>();
    list.add(4);

    EvenOnlySortStrategy<Integer> strategy =
        new EvenOnlySortStrategy<>(new MergeSortStrategy<>(), value -> value);

    strategy.sort(list, Integer::compare);

    assertEquals(4, list.get(0));
    assertEquals(1, list.size());
  }

  @Test
  void shouldKeepSingleOddElementUnchanged() {
    MyList<Integer> list = new MyLinkedList<>();
    list.add(7);

    EvenOnlySortStrategy<Integer> strategy =
        new EvenOnlySortStrategy<>(new MergeSortStrategy<>(), value -> value);

    strategy.sort(list, Integer::compare);

    assertEquals(7, list.get(0));
    assertEquals(1, list.size());
  }

  @Test
  void shouldHandleDuplicateEvenValues() {
    MyList<Integer> list = new MyLinkedList<>();

    list.add(8);
    list.add(4);
    list.add(8);
    list.add(2);
    list.add(4);

    EvenOnlySortStrategy<Integer> strategy =
        new EvenOnlySortStrategy<>(new MergeSortStrategy<>(), value -> value);

    strategy.sort(list, Integer::compare);

    assertEquals(2, list.get(0));
    assertEquals(4, list.get(1));
    assertEquals(4, list.get(2));
    assertEquals(8, list.get(3));
    assertEquals(8, list.get(4));
  }

  @Test
  void shouldKeepOddStudentObjectsAtSameIndexes() {
    Student oddFirst = new Student("A01", 4.0, 7);
    Student evenFirst = new Student("A02", 4.0, 8);
    Student oddSecond = new Student("A03", 4.0, 3);
    Student evenSecond = new Student("A04", 4.0, 2);
    Student oddThird = new Student("A05", 4.0, 5);
    Student evenThird = new Student("A06", 4.0, 4);

    MyList<Student> students = new MyLinkedList<>();

    students.add(oddFirst);
    students.add(evenFirst);
    students.add(oddSecond);
    students.add(evenSecond);
    students.add(oddThird);
    students.add(evenThird);

    EvenOnlySortStrategy<Student> strategy =
        new EvenOnlySortStrategy<>(new MergeSortStrategy<>(), Student::getRecordBookNumber);

    strategy.sort(students, new StudentRecordBookComparator());

    assertSame(oddFirst, students.get(0));
    assertSame(oddSecond, students.get(2));
    assertSame(oddThird, students.get(4));

    assertEquals(2, students.get(1).getRecordBookNumber());
    assertEquals(4, students.get(3).getRecordBookNumber());
    assertEquals(8, students.get(5).getRecordBookNumber());
  }

  @Test
  void shouldSortWhenStrategyPassedThroughConstructor() {
    MyList<Integer> list = new MyLinkedList<>();

    list.add(8);
    list.add(2);
    list.add(4);

    EvenOnlySortStrategy<Integer> strategy = new EvenOnlySortStrategy<>(new MergeSortStrategy<>());

    strategy.setValueExtractor(value -> value);

    strategy.sort(list, Integer::compare);

    assertEquals(2, list.get(0));
    assertEquals(4, list.get(1));
    assertEquals(8, list.get(2));
  }

  @Test
  void shouldSortOnlyEvenElements() {
    MyList<Student> students = new MyLinkedList<>();

    students.add(new Student("A01", 4.0, 7));
    students.add(new Student("A02", 4.0, 8));
    students.add(new Student("A03", 4.0, 3));
    students.add(new Student("A04", 4.0, 2));
    students.add(new Student("A05", 4.0, 5));
    students.add(new Student("A06", 4.0, 4));

    EvenOnlySortStrategy<Student> strategy =
        new EvenOnlySortStrategy<>(new MergeSortStrategy<>(), Student::getRecordBookNumber);

    strategy.sort(students, new StudentRecordBookComparator());

    assertEquals(7, students.get(0).getRecordBookNumber());
    assertEquals(2, students.get(1).getRecordBookNumber());
    assertEquals(3, students.get(2).getRecordBookNumber());
    assertEquals(4, students.get(3).getRecordBookNumber());
    assertEquals(5, students.get(4).getRecordBookNumber());
    assertEquals(8, students.get(5).getRecordBookNumber());

    assertEquals(6, students.size());
  }

  @Test
  void shouldSortAllEvenElements() {
    MyList<Integer> list = new MyLinkedList<>();

    list.add(8);
    list.add(2);
    list.add(6);
    list.add(4);

    EvenOnlySortStrategy<Integer> strategy =
        new EvenOnlySortStrategy<>(new MergeSortStrategy<>(), value -> value);

    strategy.sort(list, Integer::compare);

    assertEquals(2, list.get(0));
    assertEquals(4, list.get(1));
    assertEquals(6, list.get(2));
    assertEquals(8, list.get(3));
  }

  @Test
  void shouldKeepOddElementsUnchanged() {
    MyList<Integer> list = new MyLinkedList<>();

    list.add(7);
    list.add(3);
    list.add(5);
    list.add(1);

    EvenOnlySortStrategy<Integer> strategy =
        new EvenOnlySortStrategy<>(new MergeSortStrategy<>(), value -> value);

    strategy.sort(list, Integer::compare);

    assertEquals(7, list.get(0));
    assertEquals(3, list.get(1));
    assertEquals(5, list.get(2));
    assertEquals(1, list.get(3));
  }

  @Test
  void shouldThrowWhenSortStrategyIsNotSet() {
    EvenOnlySortStrategy<Integer> strategy = new EvenOnlySortStrategy<>();

    strategy.setValueExtractor(value -> value);

    MyList<Integer> list = new MyLinkedList<>();
    list.add(2);
    list.add(4);

    assertThrows(IllegalStateException.class, () -> strategy.sort(list, Integer::compare));
  }

  @Test
  void shouldRejectNullSortStrategy() {
    EvenOnlySortStrategy<Integer> strategy = new EvenOnlySortStrategy<>();

    assertThrows(IllegalArgumentException.class, () -> strategy.setSortStrategy(null));
  }

  @Test
  void shouldDelegateSortingToProvidedStrategy() {
    MyList<Integer> list = new MyLinkedList<>();

    list.add(7);
    list.add(8);
    list.add(3);
    list.add(2);

    RecordingSortStrategy recordingStrategy = new RecordingSortStrategy();

    EvenOnlySortStrategy<Integer> strategy =
        new EvenOnlySortStrategy<>(recordingStrategy, value -> value);

    strategy.sort(list, Integer::compare);

    assertTrue(recordingStrategy.wasCalled);
    assertEquals(2, recordingStrategy.receivedSize);
    assertEquals(8, recordingStrategy.firstReceivedElement);
    assertEquals(2, recordingStrategy.secondReceivedElement);
  }

  @Test
  void shouldUseNewStrategyAfterReplacement() {
    RecordingSortStrategy firstStrategy = new RecordingSortStrategy();

    RecordingSortStrategy secondStrategy = new RecordingSortStrategy();

    EvenOnlySortStrategy<Integer> strategy =
        new EvenOnlySortStrategy<>(firstStrategy, value -> value);

    MyList<Integer> firstList = new MyLinkedList<>();
    firstList.add(4);
    firstList.add(2);

    strategy.sort(firstList, Integer::compare);

    assertTrue(firstStrategy.wasCalled);

    strategy.setSortStrategy(secondStrategy);

    MyList<Integer> secondList = new MyLinkedList<>();
    secondList.add(8);
    secondList.add(6);

    strategy.sort(secondList, Integer::compare);

    assertTrue(secondStrategy.wasCalled);
  }

  private static class RecordingSortStrategy implements SortStrategy<Integer> {

    private boolean wasCalled;
    private int receivedSize;
    private Integer firstReceivedElement;
    private Integer secondReceivedElement;

    @Override
    public void sort(MyList<Integer> list, Comparator<Integer> comparator) {
      wasCalled = true;
      receivedSize = list.size();

      if (list.size() > 0) {
        firstReceivedElement = list.get(0);
      }

      if (list.size() > 1) {
        secondReceivedElement = list.get(1);
      }
    }
  }
}
