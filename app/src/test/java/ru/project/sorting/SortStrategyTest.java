package ru.project.sorting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;

class SortStrategyTest {

  private MyList<Student> students;
  private final Comparator<Student> groupComparator = new StudentGroupComparator();
  private final Comparator<Student> gradeComparator = new StudentAverageGradeComparator();
  private final Comparator<Student> recordComparator = new StudentRecordBookComparator();

  @BeforeEach
  void setUp() {
    students = new MyLinkedList<>();
    students.add(new Student("A12", 4.5, 12345));
    students.add(new Student("B32", 3.8, 12346));
    students.add(new Student("Z00", 4.9, 12347));
    students.add(new Student("A12", 4.2, 12348));
    students.add(new Student("B32", 5.0, 12349));
  }

  @Test
  void testMergeSortByGroupNumber() {
    SortStrategy<Student> strategy = new MergeSortStrategy<>();
    strategy.sort(students, groupComparator);
    assertOrder(students, "A12", "A12", "B32", "B32", "Z00");
  }

  @Test
  void testMergeSortByAverageGrade() {
    SortStrategy<Student> strategy = new MergeSortStrategy<>();
    strategy.sort(students, gradeComparator);
    assertOrder(students, 3.8, 4.2, 4.5, 4.9, 5.0);
  }

  @Test
  void testMergeSortByRecordBookNumber() {
    SortStrategy<Student> strategy = new MergeSortStrategy<>();
    strategy.sort(students, recordComparator);
    assertOrder(students, 12345, 12346, 12347, 12348, 12349);
  }

  @Test
  void testBubbleSortByGroupNumber() {
    SortStrategy<Student> strategy = new BubbleSortStrategy<>();
    strategy.sort(students, groupComparator);
    assertOrder(students, "A12", "A12", "B32", "B32", "Z00");
  }

  @Test
  void testBubbleSortByAverageGrade() {
    SortStrategy<Student> strategy = new BubbleSortStrategy<>();
    strategy.sort(students, gradeComparator);
    assertOrder(students, 3.8, 4.2, 4.5, 4.9, 5.0);
  }

  @Test
  void testBubbleSortByRecordBookNumber() {
    SortStrategy<Student> strategy = new BubbleSortStrategy<>();
    strategy.sort(students, recordComparator);
    assertOrder(students, 12345, 12346, 12347, 12348, 12349);
  }

  @Test
  void testEvenOnlySortByRecordBookNumber() {
    MyList<Student> list = new MyLinkedList<>();
    list.add(new Student("A01", 4.5, 7));
    list.add(new Student("A02", 4.5, 8));
    list.add(new Student("A03", 4.5, 3));
    list.add(new Student("A04", 4.5, 2));
    list.add(new Student("A05", 4.5, 5));
    list.add(new Student("A06", 4.5, 4));

    EvenOnlyMergeSortStrategy<Student> strategy =
        new EvenOnlyMergeSortStrategy<>(Student::getRecordBookNumber);
    strategy.sort(list, Comparator.comparingInt(Student::getRecordBookNumber));

    int[] expected = {7, 2, 3, 4, 5, 8};
    for (int i = 0; i < list.size(); i++) {
      assertEquals(expected[i], list.get(i).getRecordBookNumber());
    }
  }

  @Test
  void testEvenOnlyWithNoEvenElements() {
    MyList<Student> list = new MyLinkedList<>();
    list.add(new Student("A01", 4.5, 1));
    list.add(new Student("A02", 4.5, 3));
    list.add(new Student("A03", 4.5, 5));

    EvenOnlyMergeSortStrategy<Student> strategy =
        new EvenOnlyMergeSortStrategy<>(Student::getRecordBookNumber);
    strategy.sort(list, Comparator.comparingInt(Student::getRecordBookNumber));

    int[] expected = {1, 3, 5};
    for (int i = 0; i < list.size(); i++) {
      assertEquals(expected[i], list.get(i).getRecordBookNumber());
    }
  }

  @Test
  void testEvenOnlyWithAllEvenElements() {
    MyList<Student> list = new MyLinkedList<>();
    list.add(new Student("A01", 4.5, 4));
    list.add(new Student("A02", 4.5, 2));
    list.add(new Student("A03", 4.5, 6));

    EvenOnlyMergeSortStrategy<Student> strategy =
        new EvenOnlyMergeSortStrategy<>(Student::getRecordBookNumber);
    strategy.sort(list, Comparator.comparingInt(Student::getRecordBookNumber));

    int[] expected = {2, 4, 6};
    for (int i = 0; i < list.size(); i++) {
      assertEquals(expected[i], list.get(i).getRecordBookNumber());
    }
  }

  @Test
  void testSortEmptyList() {
    MyList<Student> empty = new MyLinkedList<>();
    SortStrategy<Student> strategy = new MergeSortStrategy<>();
    strategy.sort(empty, groupComparator);
    assertEquals(0, empty.size());
  }

  @Test
  void testSortSingleElement() {
    MyList<Student> single = new MyLinkedList<>();
    single.add(new Student("A12", 4.5, 12345));
    SortStrategy<Student> strategy = new MergeSortStrategy<>();
    strategy.sort(single, groupComparator);
    assertEquals(1, single.size());
  }

  private void assertOrder(MyList<Student> list, String... groups) {
    assertEquals(groups.length, list.size());
    for (int i = 0; i < groups.length; i++) {
      assertEquals(groups[i], list.get(i).getGroupNumber());
    }
  }

  private void assertOrder(MyList<Student> list, double... grades) {
    assertEquals(grades.length, list.size());
    for (int i = 0; i < grades.length; i++) {
      assertEquals(grades[i], list.get(i).getAverageGrade(), 0.001);
    }
  }

  private void assertOrder(MyList<Student> list, int... records) {
    assertEquals(records.length, list.size());
    for (int i = 0; i < records.length; i++) {
      assertEquals(records[i], list.get(i).getRecordBookNumber());
    }
  }
}
