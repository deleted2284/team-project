package ru.project.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UncheckedIOException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.project.student.Student;

class StudentCsvReaderTest {
  private final String ABSOLUTE_DIR = "C:\\Users\\Dexp\\TestData\\"
  private StudentCsvReader valideReader;

  // Testing StudentCsvReader
  @BeforeEach
  void setUp() {
    valideReader = new StudentCsvReader(ABSOLUTE_DIR + "sample.csv");
  }

  @Test
  void readerTestRejectRelativePath() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new StudentCsvReader("/Users/Dexp/TestData/sample.csv"));
  }

  @Test
  void readerRejectPathIsDir() {
    assertThrows(
        UncheckedIOException.class, () -> new StudentCsvReader(ABSOLUTE_DIR));
  }

  @Test
  void readerTestFileNotExist() {
    assertThrows(
        UncheckedIOException.class,
        () -> new StudentCsvReader(ABSOLUTE_DIR + "sss.csv"));
  }

  @Test
  void testSkipsHeader() {
    Student student = valideReader.next();
    assertNotEquals(student.getGroupNumber(), "groupNumber");
  }

  @Test
  void testCreationStudentOnValidData() {
    Student student = valideReader.next();
    assertEquals(student.getGroupNumber(), "A21");
    assertEquals(student.getAverageGrade(), 5.0);
    assertEquals(student.getRecordBookNumber(), 331);
  }

  @Test
  void testNotSkippingFirstStudent() {
    valideReader.hasNext();
    valideReader.hasNext();
    Student student = valideReader.next();
    assertEquals(student.getGroupNumber(), "A21");
    assertEquals(student.getAverageGrade(), 5.0);
    assertEquals(student.getRecordBookNumber(), 331);
  }

  @Test
  void testValidEndsOfFile() {
    valideReader.next();
    valideReader.next();
    assertTrue(!valideReader.hasNext());
  }

  @Test
  void testOutOfIndex() {
    assertThrows(
        NoSuchElementException.class,
        () -> {
          Student student = valideReader.next();
          student = valideReader.next();
          student = valideReader.next();
        });
  }

  @Test
  void readerTestRejectFewParametres() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          StudentCsvReader reader =
              new StudentCsvReader(ABSOLUTE_DIR + "fewparameters.csv");
          Student student = reader.next();
        });
  }

  @Test
  void readerTestRejectInvalidGroupNumber() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          StudentCsvReader reader =
              new StudentCsvReader(ABSOLUTE_DIR + "invalid_groupnumber.csv");
          Student student = reader.next();
        });
  }

  @Test
  void readerTestRejectInvalidAverageGrade() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          StudentCsvReader reader =
              new StudentCsvReader(ABSOLUTE_DIR + "invalid_averagegrade.csv");
          Student student = reader.next();
        });
  }

  @Test
  void readerTestRejectInvalidRecordBookNumber() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          StudentCsvReader reader =
              new StudentCsvReader(ABSOLUTE_DIR + "invalid_recordbooknumber.csv");
          Student student = reader.next();
        });
  }
}
