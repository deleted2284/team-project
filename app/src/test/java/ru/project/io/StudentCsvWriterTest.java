package ru.project.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;

class StudentCsvWriterTest {

  @TempDir Path tempDir;

  @Test
  void writeShouldWriteHeaderAndStudentToEmptyFile() throws Exception {
    Path file = tempDir.resolve("students.csv");
    Files.createFile(file);

    Student student = new Student("A12", 4.5, 10001);

    StudentCsvWriter writer = new StudentCsvWriter(file.toAbsolutePath().toString());

    writer.write(student);

    List<String> lines = Files.readAllLines(file);

    assertEquals(List.of("groupNumber,averageGrade,recordBookNumber", "A12,4.5,10001"), lines);
  }

  @Test
  void writeShouldAppendStudentToExistingFile() throws Exception {
    Path file = tempDir.resolve("students.csv");

    Files.write(file, List.of("groupNumber,averageGrade,recordBookNumber", "A12,4.5,10001"));

    Student student = new Student("B07", 3.8, 10002);

    StudentCsvWriter writer = new StudentCsvWriter(file.toAbsolutePath().toString());

    writer.write(student);

    List<String> lines = Files.readAllLines(file);

    assertEquals(
        List.of("groupNumber,averageGrade,recordBookNumber", "A12,4.5,10001", "B07,3.8,10002"),
        lines);
  }

  @Test
  void writeShouldNotCreateMissingFile() {
    Path file = tempDir.resolve("students.csv");

    Student student = new Student("A12", 4.5, 10001);

    StudentCsvWriter writer = new StudentCsvWriter(file.toAbsolutePath().toString());

    assertThrows(IOException.class, () -> writer.write(student));

    assertFalse(Files.exists(file));
  }

  @Test
  void writeShouldAppendSeveralStudentsInOrder() throws Exception {
    Path file = tempDir.resolve("students.csv");
    Files.createFile(file);

    Student student1 = new Student("A12", 4.5, 10001);
    Student student2 = new Student("B07", 3.8, 10002);
    Student student3 = new Student("C25", 4.9, 10003);

    StudentCsvWriter writer = new StudentCsvWriter(file.toAbsolutePath().toString());

    writer.write(student1);
    writer.write(student2);
    writer.write(student3);

    List<String> lines = Files.readAllLines(file);

    assertEquals(
        List.of(
            "groupNumber,averageGrade,recordBookNumber",
            "A12,4.5,10001",
            "B07,3.8,10002",
            "C25,4.9,10003"),
        lines);
  }

  @Test
  void writeArrayShouldAppendStudentsFromAllListsInOrder() throws Exception {

    Path file = tempDir.resolve("students.csv");
    Files.createFile(file);

    Student student1 = new Student("A12", 4.5, 10001);
    Student student2 = new Student("B07", 3.8, 10002);
    Student student3 = new Student("C25", 4.9, 10003);
    Student student4 = new Student("D15", 4.2, 10004);
    Student student5 = new Student("E20", 3.6, 10005);

    MyList<Student> firstList = new MyLinkedList<>();
    firstList.add(student1);
    firstList.add(student2);

    MyList<Student> secondList = new MyLinkedList<>();
    secondList.add(student3);

    MyList<Student> thirdList = new MyLinkedList<>();
    thirdList.add(student4);
    thirdList.add(student5);

    @SuppressWarnings("unchecked")
    MyList<Student>[] students = new MyList[] {firstList, secondList, thirdList};

    StudentCsvWriter writer = new StudentCsvWriter(file.toAbsolutePath().toString());

    writer.write(students);

    List<String> lines = Files.readAllLines(file);

    assertEquals(
        List.of(
            "groupNumber,averageGrade,recordBookNumber",
            "A12,4.5,10001",
            "B07,3.8,10002",
            "C25,4.9,10003",
            "D15,4.2,10004",
            "E20,3.6,10005"),
        lines);
  }

  @Test
  void writeArrayShouldSkipEmptyLists() throws Exception {
    Path file = tempDir.resolve("students.csv");
    Files.createFile(file);

    Student student = new Student("A12", 4.5, 10001);

    MyList<Student> emptyList = new MyLinkedList<>();

    MyList<Student> nonEmptyList = new MyLinkedList<>();
    nonEmptyList.add(student);

    @SuppressWarnings("unchecked")
    MyList<Student>[] students = new MyList[] {emptyList, nonEmptyList, new MyLinkedList<>()};

    StudentCsvWriter writer = new StudentCsvWriter(file.toAbsolutePath().toString());

    writer.write(students);

    List<String> lines = Files.readAllLines(file);

    assertEquals(List.of("groupNumber,averageGrade,recordBookNumber", "A12,4.5,10001"), lines);
  }

  @Test
  void writeEmptyArrayShouldNotModifyFile() throws Exception {
    Path file = tempDir.resolve("students.csv");

    List<String> initialContent =
        List.of("groupNumber,averageGrade,recordBookNumber", "A12,4.5,10001");

    Files.write(file, initialContent);

    @SuppressWarnings("unchecked")
    MyList<Student>[] students = new MyList[0];

    StudentCsvWriter writer = new StudentCsvWriter(file.toAbsolutePath().toString());

    writer.write(students);

    assertEquals(initialContent, Files.readAllLines(file));
  }

  @Test
  void writeEmptyArrayShouldNotCreateMissingFile() {
    Path file = tempDir.resolve("students.csv");

    @SuppressWarnings("unchecked")
    MyList<Student>[] students = new MyList[0];

    StudentCsvWriter writer = new StudentCsvWriter(file.toAbsolutePath().toString());

    assertThrows(IOException.class, () -> writer.write(students));

    assertFalse(Files.exists(file));
  }
}
