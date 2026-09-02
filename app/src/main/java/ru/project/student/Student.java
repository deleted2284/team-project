package ru.project.student;

import java.util.Objects;

public class Student {

  public static final double MIN_AVERAGE_GRADE = 0.0;
  public static final double MAX_AVERAGE_GRADE = 5.0;
  public static final int MIN_RECORD_BOOK_NUMBER = 1;
  public static final String GROUP_NUMBER_PATTERN = "^[A-Z][0-9]{2}$";

  private final String groupNumber;
  private final double averageGrade;
  private final int recordBookNumber;

  public Student(String groupNumber, double averageGrade, int recordBookNumber) {
    if (groupNumber == null || !groupNumber.matches(GROUP_NUMBER_PATTERN)) {
      throw new IllegalArgumentException(
          "Group number must match pattern: " + GROUP_NUMBER_PATTERN);
    }
    if (averageGrade < MIN_AVERAGE_GRADE || averageGrade > MAX_AVERAGE_GRADE) {
      throw new IllegalArgumentException(
          "Average grade must be between " + MIN_AVERAGE_GRADE + " and " + MAX_AVERAGE_GRADE);
    }
    if (recordBookNumber <= 0) {
      throw new IllegalArgumentException("Record book number must be positive");
    }
    if (recordBookNumber < MIN_RECORD_BOOK_NUMBER) {
      throw new IllegalArgumentException(
          "Record book number must be at least " + MIN_RECORD_BOOK_NUMBER);
    }
    this.groupNumber = groupNumber;
    this.averageGrade = averageGrade;
    this.recordBookNumber = recordBookNumber;
  }

  public String getGroupNumber() {
    return groupNumber;
  }

  public double getAverageGrade() {
    return averageGrade;
  }

  public int getRecordBookNumber() {
    return recordBookNumber;
  }

  public Student withGroupNumber(String groupNumber) {
    return new Student(groupNumber, this.averageGrade, this.recordBookNumber);
  }

  public Student withAverageGrade(double averageGrade) {
    return new Student(this.groupNumber, averageGrade, this.recordBookNumber);
  }

  public Student withRecordBookNumber(int recordBookNumber) {
    return new Student(this.groupNumber, this.averageGrade, recordBookNumber);
  }

  public static double getMinAverageGrade() {
    return MIN_AVERAGE_GRADE;
  }

  public static double getMaxAverageGrade() {
    return MAX_AVERAGE_GRADE;
  }

  public static int getMinRecordBookNumber() {
    return MIN_RECORD_BOOK_NUMBER;
  }

  public static String getGroupNumberPattern() {
    return GROUP_NUMBER_PATTERN;
  }

  public String toString() {
    return "Student{"
        + "groupNumber='"
        + groupNumber
        + '\''
        + ", averageGrade="
        + averageGrade
        + ", recordBookNumber="
        + recordBookNumber
        + '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupNumber, averageGrade, recordBookNumber);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    if (this.hashCode() != obj.hashCode()) {
      return false;
    }

    Student student = (Student) obj;
    return Double.compare(averageGrade, student.averageGrade) == 0
        && recordBookNumber == student.recordBookNumber
        && Objects.equals(groupNumber, student.groupNumber);
  }
}
