package ru.project.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.project.student.Student;
import ru.project.student.StudentBuilder;
  public class StudentCsvReader implements Iterator<Student>, AutoCloseable {
  private static final String GROUP_NUMBER = "groupNumber";
  private static final String AVERAGE_GRADE = "averageGrade";
  private static final String RECORD_BOOK_NUMBER = "recordBookNumber";
  private final CSVParser parser;
  private final Iterator<CSVRecord> records;
  private int lineOfFile=0;
    public StudentCsvReader(Path path) {
      if (!path.isAbsolute()) {
        throw new IllegalArgumentException("File path must be absolute");
      }
      try {
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .get();
        this.parser = CSVParser.parse(path, StandardCharsets.UTF_8, format);
        validateHeader(parser.getHeaderMap());
        this.records = parser.iterator();
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
    @Override
    public boolean hasNext() {
      return records.hasNext();
    }
    @Override
    public Student next() {
      if (!records.hasNext()) {
        throw new NoSuchElementException("No more records at line " + (lineOfFile + 1));
      }
      lineOfFile++;
      CSVRecord record = records.next();
      if (record.size() != 3) {
        throw new IllegalArgumentException(
                "Problem at line " + lineOfFile + ". Expected 3 columns, but got " + record.size());
      }
      String groupNumber = record.get(GROUP_NUMBER);
      String averageGradeStr = record.get(AVERAGE_GRADE);
      String recordBookNumberStr = record.get(RECORD_BOOK_NUMBER);
      double averageGrade;
      int recordBookNumber;
      try {
        averageGrade = Double.parseDouble(averageGradeStr);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(
                "Problem at line " + lineOfFile + ": cannot parse averageGrade '" + averageGradeStr + "' as double", e);
      }
      try {
        recordBookNumber = Integer.parseInt(recordBookNumberStr);
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException(
                "Problem at line " + lineOfFile + ": cannot parse recordBookNumber '" + recordBookNumberStr + "' as int", e);
      }
      return new StudentBuilder()
              .setGroupNumber(groupNumber)
              .setAverageGrade(averageGrade)
              .setRecordBookNumber(recordBookNumber)
              .build();
    }
    @Override
    public void close() throws IOException {
      parser.close();
    }
    private static void validateHeader(Map<String, Integer> headerMap) {
      String[] expected = {GROUP_NUMBER, AVERAGE_GRADE, RECORD_BOOK_NUMBER};
      if (headerMap.size() != expected.length
              || !headerMap.keySet().containsAll(Arrays.asList(expected))) {
        throw new IllegalArgumentException("Invalid CSV header");
      }
    }
  }
