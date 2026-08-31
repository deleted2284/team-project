package ru.project.finder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;

import static org.junit.jupiter.api.Assertions.*;

public class StudentOccurrenceIndexFinderTest
{
    private StudentOccurrenceIndexFinder finder;
    private MyList<Student> students;

    @BeforeEach
    void setUp() {
        finder = new StudentOccurrenceIndexFinder();
        students = new MyLinkedList<>();
    }

    @Test
    void shouldTesfindOccurrences_emptyList_returnsEmptyArray() {
        int[] result = finder.findOccurrences(students, new Student("A12", 4.5, 1));
        assertEquals(0, result.length);
    }
    @Test
    void shouldTesfindOccurrences_noMatches_returnsEmptyArray() {
        students.add(new Student("A12", 4.0, 101));
        students.add(new Student("B23", 3.5, 102));
        Student target = new Student("C34", 4.8, 103); // такого нет

        int[] result = finder.findOccurrences(students, target);
        assertEquals(0, result.length);
    }
    @Test
    void shouldTesfindOccurrences_singleMatch_returnsCorrectIndex() {
        students.add(new Student("A12", 4.0, 101));
        students.add(new Student("B23", 3.5, 102)); // target здесь
        students.add(new Student("C34", 4.8, 103));

        Student target = new Student("B23", 3.5, 102);
        int[] result = finder.findOccurrences(students, target);

        assertEquals(1, result.length);
        assertEquals(1, result[0]);
    }
    @Test
    void shouldTesfindOccurrences_multipleMatches_returnsAllIndicesSorted() {
        // индексы: 0, 2, 4 — должны вернуться в порядке возрастания
        students.add(new Student("A12", 4.5, 101)); // match
        students.add(new Student("B23", 3.8, 102));
        students.add(new Student("A12", 4.5, 101)); // match
        students.add(new Student("C34", 4.0, 103));
        students.add(new Student("A12", 4.5, 101)); // match

        Student target = new Student("A12", 4.5, 101);
        int[] result = finder.findOccurrences(students, target);

        assertEquals(3, result.length);
        assertArrayEquals(new int[]{0, 2, 4}, result);
    }

    @Test
    void shouldTesfindOccurrences_allElementsMatch_returnsAllIndices() {
        Student s = new Student("X99", 5.0, 999);
        students.add(s);
        students.add(s);
        students.add(s);

        int[] result = finder.findOccurrences(students, s);
        assertEquals(3, result.length);
        assertArrayEquals(new int[]{0, 1, 2}, result);
    }

    @Test
    void shouldTesfindOccurrences_differentAverageGrade_noMatch() {
        students.add(new Student("A12", 4.50, 101));
        Student target = new Student("A12", 4.51, 101); // отличается балл

        int[] result = finder.findOccurrences(students, target);
        assertEquals(0, result.length);
    }

    @Test
    void shouldTesfindOccurrences_differentGroupNumber_noMatch() {
        students.add(new Student("A12", 4.5, 101));
        Student target = new Student("B12", 4.5, 101); // другая группа

        int[] result = finder.findOccurrences(students, target);
        assertEquals(0, result.length);
    }

    @Test
    void shouldTesfindOccurrences_differentRecordBookNumber_noMatch() {
        students.add(new Student("A12", 4.5, 101));
        Student target = new Student("A12", 4.5, 102); // другой номер зачетки

        int[] result = finder.findOccurrences(students, target);
        assertEquals(0, result.length);
    }


    @Test
    void shouldTesfindOccurrences_nullList_throwsException() {
        Student target = new Student("A12", 4.5, 101);
        assertThrows(IllegalArgumentException.class, () -> finder.findOccurrences(null, target));
    }


    @Test
    void shouldTesfindOccurrences_nullTarget_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> finder.findOccurrences(students, null));
    }
    @Test
    void shouldTesfindOccurrences_resultIsSorted() {
        String[] validGroups = {"A00", "A01", "B12", "C99", "X55"};

        for (int i = 0; i < 50; i++) {
            String group = validGroups[i % validGroups.length];
            double grade = (3.0 + (i % 3)) / 2.0;
            if (i % 3 == 0) {

                students.add(new Student("A12", 4.5, 101));
            } else {
                students.add(new Student(group, grade, 200 + i));
            }
        }
        Student target = new Student("A12", 4.5, 101);
        int[] result = finder.findOccurrences(students, target);


        for (int i = 1; i < result.length; i++) {
            assertTrue(result[i] > result[i - 1], "Результат должен быть отсортирован");
        }
    }
}
