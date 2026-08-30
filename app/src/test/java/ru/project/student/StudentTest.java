package ru.project.student;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class StudentTest {

  @Test
  void testValidStudentCreation() {
    Student student = new Student("A12", 4.5, 12345);
    assertEquals("A12", student.getGroupNumber());
    assertEquals(4.5, student.getAverageGrade());
    assertEquals(12345, student.getRecordBookNumber());
  }

  @Test
  void testValidGroupNumbers() {
    assertDoesNotThrow(() -> new Student("A12", 4.5, 1));
    assertDoesNotThrow(() -> new Student("B32", 4.5, 1));
    assertDoesNotThrow(() -> new Student("Z00", 4.5, 1));
  }

  @Test
  void testInvalidGroupNumbers() {
    assertThrows(IllegalArgumentException.class, () -> new Student("a12", 4.5, 1));
    assertThrows(IllegalArgumentException.class, () -> new Student("A1", 4.5, 1));
    assertThrows(IllegalArgumentException.class, () -> new Student("A123", 4.5, 1));
    assertThrows(IllegalArgumentException.class, () -> new Student("AA1", 4.5, 1));
    assertThrows(IllegalArgumentException.class, () -> new Student("а1А", 4.5, 1));
    assertThrows(IllegalArgumentException.class, () -> new Student("аа!!а123", 4.5, 1));
    assertThrows(IllegalArgumentException.class, () -> new Student("23фи!12", 4.5, 1));
    assertThrows(IllegalArgumentException.class, () -> new Student("№№###*()", 4.5, 1));
  }

  @Test
  void testValidAverageGrades() {
    assertDoesNotThrow(() -> new Student("A12", 0.0, 1));
    assertDoesNotThrow(() -> new Student("A12", 5.0, 1));
  }

  @Test
  void testInvalidAverageGrades() {
    assertThrows(IllegalArgumentException.class, () -> new Student("A12", -1.0, 1));
    assertThrows(IllegalArgumentException.class, () -> new Student("A12", 6.0, 1));
  }

  @Test
  void testValidRecordBookNumbers() {
    assertDoesNotThrow(() -> new Student("A12", 4.5, 1));
    assertDoesNotThrow(() -> new Student("A12", 4.5, 12345));
  }

  @Test
  void testInvalidRecordBookNumbers() {
    assertThrows(IllegalArgumentException.class, () -> new Student("A12", 4.5, 0));
    assertThrows(IllegalArgumentException.class, () -> new Student("A12", 4.5, -1));
  }

  @Test
  void testWithGroupNumber() {
    Student original = new Student("A12", 4.5, 12345);
    Student updated = original.withGroupNumber("B32");

    assertNotSame(original, updated);
    assertEquals("B32", updated.getGroupNumber());
    assertEquals(4.5, updated.getAverageGrade());
    assertEquals(12345, updated.getRecordBookNumber());

    assertEquals("A12", original.getGroupNumber());
  }

  @Test
  void testWithAverageGrade() {
    Student original = new Student("A12", 4.5, 12345);
    Student updated = original.withAverageGrade(4.8);

    assertNotSame(original, updated);
    assertEquals(4.8, updated.getAverageGrade());
    assertEquals("A12", updated.getGroupNumber());
    assertEquals(12345, updated.getRecordBookNumber());

    assertEquals(4.5, original.getAverageGrade());
  }

  @Test
  void testWithRecordBookNumber() {
    Student original = new Student("A12", 4.5, 12345);
    Student updated = original.withRecordBookNumber(99999);

    assertNotSame(original, updated);
    assertEquals(99999, updated.getRecordBookNumber());
    assertEquals("A12", updated.getGroupNumber());
    assertEquals(4.5, updated.getAverageGrade());

    assertEquals(12345, original.getRecordBookNumber());
  }

  @Test
  void testWithInvalidGroupNumber() {
    Student original = new Student("A12", 4.5, 12345);
    assertThrows(IllegalArgumentException.class, () -> original.withGroupNumber("a12"));
    assertEquals("A12", original.getGroupNumber()); // не изменился
  }

  @Test
  void testWithInvalidAverageGrade() {
    Student original = new Student("A12", 4.5, 12345);
    assertThrows(IllegalArgumentException.class, () -> original.withAverageGrade(6.0));
    assertEquals(4.5, original.getAverageGrade());
  }

  @Test
  void testWithInvalidRecordBookNumber() {
    Student original = new Student("A12", 4.5, 12345);
    assertThrows(IllegalArgumentException.class, () -> original.withRecordBookNumber(0));
    assertEquals(12345, original.getRecordBookNumber());
  }

  @Test
  void testEqualsAndHashCode() {
    Student s1 = new Student("A12", 4.5, 12345);
    Student s2 = new Student("A12", 4.5, 12345);
    Student s3 = new Student("B32", 4.5, 12345);
    Student s4 = new Student("A12", 4.8, 12345);
    Student s5 = new Student("A12", 4.5, 99999);

    assertEquals(s1, s2);
    assertEquals(s1.hashCode(), s2.hashCode());

    assertNotEquals(s1, s3);
    assertNotEquals(s1, s4);
    assertNotEquals(s1, s5);
    assertNotEquals(s1, null);
    assertNotEquals(s1, "some string");
  }
}
