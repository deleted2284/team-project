package ru.project.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.Test;
import ru.project.student.Student;

class StudentCsvReaderTest {
  @TempDir Path tempDir;
  private Path studentPath;

  // Testing StudentCsvReader

  @BeforeAll
  void generateStudentTestFile(){
    studentPath = tempDir.resolve("students.csv");
    Files.write(
            studentPath,
            new String[]{
                    "groupNumber,averageGrade,recordBookNumber",
                    "A21,5.0,331",
                    "A21,4.0,332"
            },
            StandardCharsets.UTF_8
    );
  }

  @BeforeEach
  void setUp() {
    valideReader = new StudentCsvReader(studentPath.toAbsolutePath().toString());
  }

  @Test
  void readerTestRejectRelativePath() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new StudentCsvReader(studentPath.toString()));
  }

  @Test
  void readerRejectPathIsDir() {
    assertThrows(
        UncheckedIOException.class, () -> new StudentCsvReader(tempDir.toAbsolutePath()));
  }

  @Test
  void readerTestFileNotExist() {
    assertThrows(
        UncheckedIOException.class,
        () -> new StudentCsvReader(tempDir.resolve("sss.csv").toAbsolutePath().toString()));
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
          Path path = tempDir.resolve("fewparameters.csv");
          Files.write(
                  path,
                  new String[]{
                          "groupNumber,averageGrade,recordBookNumber",
                          "A21,5.0"
                  },
                  StandardCharsets.UTF_8
          );
          StudentCsvReader reader =
              new StudentCsvReader(path.toAbsolutePath().toString());
          Student student = reader.next();
        });
  }

  @Test
  void readerTestRejectInvalidGroupNumber() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Path path = tempDir.resolve("invalid_groupnumber.csv");
          Files.write(
                  path,
                  new String[]{
                          "groupNumber,averageGrade,recordBookNumber",
                          "AAA,5.0,331",
                          "A21,4.0,332"
                  },
                  StandardCharsets.UTF_8
          );
          StudentCsvReader reader =
              new StudentCsvReader(path.toAbsolutePath().toString());
          Student student = reader.next();
        });
  }

  @Test
  void readerTestRejectInvalidAverageGrade() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Path path = tempDir.resolve("invalid_averagegrade.csv");
          Files.write(
                  path,
                  new String[]{
                          "groupNumber,averageGrade,recordBookNumber",
                          "A21,-5.0,331",
                          "A21,4.0,332"
                  },
                  StandardCharsets.UTF_8
          );
          StudentCsvReader reader =
              new StudentCsvReader(path.toAbsolutePath().toString());
          Student student = reader.next();
        });
  }

  @Test
  void readerTestRejectInvalidRecordBookNumber() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Path path = tempDir.resolve("invalid_recordbooknumber.csv");
          Files.write(
                  path,
                  new String[]{
                          "groupNumber,averageGrade,recordBookNumber",
                          "A21,5.0,-331",
                          "A21,4.0,332"
                  },
                  StandardCharsets.UTF_8
          );
          StudentCsvReader reader =
              new StudentCsvReader(path.toAbsolutePath().toString());
          Student student = reader.next();
        });
  }

  @Test
  void testInvalidHeader(){
    assertThrows(
            IllegalArgumentException.class,
            () -> {
              Path path = tempDir.resolve("invalid_header.csv");
              Files.write(
                      path,
                      new String[]{
                              "groupNumber,averageGrade,recordBookNomber",
                              "A21,5.0,-331",
                              "A21,4.0,332"
                      },
                      StandardCharsets.UTF_8
              );
              StudentCsvReader reader =
                      new StudentCsvReader(path.toAbsolutePath().toString());
              Student student = reader.next();
            });
  }
}
