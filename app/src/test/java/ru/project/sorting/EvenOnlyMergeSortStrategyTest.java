package ru.project.sorting;

import org.junit.jupiter.api.Test;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvenOnlyMergeSortStrategyTest {

    @Test
    void shouldSortOnlyEvenElements() {
        MyList<Integer> list = new MyLinkedList<>();

        list.add(7);
        list.add(8);
        list.add(3);
        list.add(2);
        list.add(5);
        list.add(4);

        EvenOnlyMergeSortStrategy<Integer> strategy =
                new EvenOnlyMergeSortStrategy<>(value -> value);

        strategy.sort(list, Integer::compare);

        assertEquals(7, list.get(0));
        assertEquals(2, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals(4, list.get(3));
        assertEquals(5, list.get(4));
        assertEquals(8, list.get(5));

        assertEquals(6, list.size());
    }

    @Test
    void shouldKeepAllOddElementsUnchanged() {
        MyList<Integer> list = new MyLinkedList<>();

        list.add(7);
        list.add(3);
        list.add(5);
        list.add(1);

        EvenOnlyMergeSortStrategy<Integer> strategy =
                new EvenOnlyMergeSortStrategy<>(value -> value);

        strategy.sort(list, Integer::compare);

        assertEquals(7, list.get(0));
        assertEquals(3, list.get(1));
        assertEquals(5, list.get(2));
        assertEquals(1, list.get(3));
    }

    @Test
    void shouldSortAllEvenElements() {
        MyList<Integer> list = new MyLinkedList<>();

        list.add(8);
        list.add(2);
        list.add(6);
        list.add(4);

        EvenOnlyMergeSortStrategy<Integer> strategy =
                new EvenOnlyMergeSortStrategy<>(value -> value);

        strategy.sort(list, Integer::compare);

        assertEquals(2, list.get(0));
        assertEquals(4, list.get(1));
        assertEquals(6, list.get(2));
        assertEquals(8, list.get(3));
    }

    @Test
    void shouldKeepSingleElementUnchanged() {
        MyList<Integer> list = new MyLinkedList<>();

        list.add(4);

        EvenOnlyMergeSortStrategy<Integer> strategy =
                new EvenOnlyMergeSortStrategy<>(value -> value);

        strategy.sort(list, Integer::compare);

        assertEquals(4, list.get(0));
        assertEquals(1, list.size());
    }

    @Test
    void shouldHandleEmptyList() {
        MyList<Integer> list = new MyLinkedList<>();

        EvenOnlyMergeSortStrategy<Integer> strategy =
                new EvenOnlyMergeSortStrategy<>(value -> value);

        strategy.sort(list, Integer::compare);

        assertEquals(0, list.size());
    }

    @Test
    void shouldUseValueExtractorToDetermineEvenElements() {
        MyList<Integer> list = new MyLinkedList<>();

        list.add(4);
        list.add(3);
        list.add(2);
        list.add(1);

        EvenOnlyMergeSortStrategy<Integer> strategy =
                new EvenOnlyMergeSortStrategy<>(value -> value + 1);

        strategy.sort(list, Integer::compare);

        assertEquals(4, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(2, list.get(2));
        assertEquals(3, list.get(3));
    }

    @Test
    void shouldUseProvidedComparator() {
        MyList<Integer> list = new MyLinkedList<>();

        list.add(7);
        list.add(2);
        list.add(3);
        list.add(8);
        list.add(5);
        list.add(4);

        EvenOnlyMergeSortStrategy<Integer> strategy =
                new EvenOnlyMergeSortStrategy<>(value -> value);

        strategy.sort(list, (first, second) -> Integer.compare(second, first));

        assertEquals(7, list.get(0));
        assertEquals(8, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals(4, list.get(3));
        assertEquals(5, list.get(4));
        assertEquals(2, list.get(5));
    }

    @Test
    void shouldUseStudentRecordBookNumberToDetermineEvenElements() {
        MyList<Student> students = new MyLinkedList<>();

        students.add(new Student("A01", 4.0, 7));
        students.add(new Student("A02", 4.0, 8));
        students.add(new Student("A03", 4.0, 3));
        students.add(new Student("A04", 4.0, 2));
        students.add(new Student("A05", 4.0, 5));
        students.add(new Student("A06", 4.0, 4));

        SortStrategy<Student> strategy = new EvenOnlyMergeSortStrategy<>(
                Student::getRecordBookNumber
        );

        strategy.sort(students, new StudentRecordBookComparator());

        assertEquals(7, students.get(0).getRecordBookNumber());
        assertEquals(2, students.get(1).getRecordBookNumber());
        assertEquals(3, students.get(2).getRecordBookNumber());
        assertEquals(4, students.get(3).getRecordBookNumber());
        assertEquals(5, students.get(4).getRecordBookNumber());
        assertEquals(8, students.get(5).getRecordBookNumber());

        assertEquals(6, students.size());
    }
}


