package ru.project.student;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class StudentTest {
    @Test
    void shouldCreateValidStudent() {
        Student student = new StudentBuilder()
                .setGroupNumber("101")
                .setAverageGrade(4.5)
                .setRecordBookNumber("12345")
                .build();

        assertEquals("101", student.getGroupNumber());
        assertEquals(4.5, student.getAverageGrade());
        assertEquals("12345", student.getRecordBookNumber());
    }

    @Test
    void shouldThrowExceptionWhenGroupNumberIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentBuilder()
                    .setGroupNumber("")
                    .setAverageGrade(4.5)
                    .setRecordBookNumber("12345")
                    .build();
        });
    }

    @Test
    void shouldThrowExceptionWhenAverageGradeIsGreaterThanFive() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentBuilder()
                    .setGroupNumber("101")
                    .setAverageGrade(6.0)
                    .setRecordBookNumber("12345")
                    .build();
        });
    }

    @Test
    void shouldThrowExceptionWhenAverageGradeIsLessThanZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentBuilder()
                    .setGroupNumber("101")
                    .setAverageGrade(-1.0)
                    .setRecordBookNumber("12345")
                    .build();
        });
    }

    @Test
    void shouldCreateStudentWhenAverageGradeIsZero() {
        Student student = new StudentBuilder()
                .setGroupNumber("101")
                .setAverageGrade(0.0)
                .setRecordBookNumber("12345")
                .build();

        assertEquals(0.0, student.getAverageGrade());
    }

    @Test
    void shouldCreateStudentWhenAverageGradeIsFive() {
        Student student = new StudentBuilder()
                .setGroupNumber("101")
                .setAverageGrade(5.0)
                .setRecordBookNumber("12345")
                .build();

        assertEquals(5.0, student.getAverageGrade());
    }

    @Test
    void shouldThrowExceptionWhenRecordBookNumberIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentBuilder()
                    .setGroupNumber("101")
                    .setAverageGrade(4.5)
                    .setRecordBookNumber("")
                    .build();
        });
    }

    @Test
    void shouldThrowExceptionWhenGroupNumberIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentBuilder()
                    .setGroupNumber(null)
                    .setAverageGrade(4.5)
                    .setRecordBookNumber("12345")
                    .build();
        });
    }

    @Test
    void shouldThrowExceptionWhenGroupNumberIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentBuilder()
                    .setGroupNumber("   ")
                    .setAverageGrade(4.5)
                    .setRecordBookNumber("12345")
                    .build();
        });
    }

    @Test
    void shouldThrowExceptionWhenRecordBookNumberIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentBuilder()
                    .setGroupNumber("101")
                    .setAverageGrade(4.5)
                    .setRecordBookNumber(null)
                    .build();
        });
    }

    @Test
    void shouldThrowExceptionWhenRecordBookNumberIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new StudentBuilder()
                    .setGroupNumber("101")
                    .setAverageGrade(4.5)
                    .setRecordBookNumber("   ")
                    .build();
        });
    }

    @Test
    void shouldReturnTrueWhenStudentsAreEqual() {
        Student student1 = new StudentBuilder()
                .setGroupNumber("101")
                .setAverageGrade(4.5)
                .setRecordBookNumber("12345")
                .build();

        Student student2 = new StudentBuilder()
                .setGroupNumber("101")
                .setAverageGrade(4.5)
                .setRecordBookNumber("12345")
                .build();

        assertEquals(student1, student2);
        assertEquals(student1.hashCode(), student2.hashCode());
    }

    @Test
    void shouldReturnFalseWhenStudentsAreDifferent() {
        Student student1 = new StudentBuilder()
                .setGroupNumber("101")
                .setAverageGrade(4.5)
                .setRecordBookNumber("12345")
                .build();

        Student student2 = new StudentBuilder()
                .setGroupNumber("102")
                .setAverageGrade(4.5)
                .setRecordBookNumber("12345")
                .build();

        assertNotEquals(student1, student2);
    }
}
