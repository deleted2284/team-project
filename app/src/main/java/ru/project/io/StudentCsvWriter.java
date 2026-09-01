package ru.project.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import ru.project.collection.MyList;
import ru.project.student.Student;

public class StudentCsvWriter {

  private static final String GROUP_NUMBER_HEADER = "groupNumber";
  private static final String AVERAGE_GRADE_HEADER = "averageGrade";
  private static final String RECORD_BOOK_NUMBER_HEADER = "recordBookNumber";

  private final String absoluteFilePath;

  public StudentCsvWriter(String absoluteFilePath) {
    this.absoluteFilePath = absoluteFilePath;
  }

  public void write(Student student) throws IOException {
    ensureFileExists();

    boolean fileIsEmpty = isFileEmpty();

    try (CSVPrinter printer = createPrinter()) {
      if (fileIsEmpty) {
        writeHeader(printer);
      }

      writeStudent(printer, student);
    }
  }

  public void write(MyList<Student>[] students) throws IOException {
    ensureFileExists();

    boolean fileIsEmpty = isFileEmpty();

    try (CSVPrinter printer = createPrinter()) {
      if (fileIsEmpty) {
        writeHeader(printer);
      }

      for (MyList<Student> studentList : students) {
        for (int i = 0; i < studentList.size(); i++) {
          writeStudent(printer, studentList.get(i));
        }
      }
    }
  }

  private CSVPrinter createPrinter() throws IOException {
    BufferedWriter writer =
        new BufferedWriter(
            new OutputStreamWriter(
                new FileOutputStream(absoluteFilePath, true), StandardCharsets.UTF_8));

    return new CSVPrinter(writer, CSVFormat.DEFAULT);
  }

  private void writeHeader(CSVPrinter printer) throws IOException {
    printer.printRecord(GROUP_NUMBER_HEADER, AVERAGE_GRADE_HEADER, RECORD_BOOK_NUMBER_HEADER);
  }

  private void writeStudent(CSVPrinter printer, Student student) throws IOException {

    printer.printRecord(
        student.getGroupNumber(), student.getAverageGrade(), student.getRecordBookNumber());
  }

  private void ensureFileExists() throws IOException {
    File file = new File(absoluteFilePath);

    if (!file.isFile()) {
      throw new IOException("CSV file does not exist: " + absoluteFilePath);
    }
  }

  private boolean isFileEmpty() {
    return new File(absoluteFilePath).length() == 0;
  }
}
