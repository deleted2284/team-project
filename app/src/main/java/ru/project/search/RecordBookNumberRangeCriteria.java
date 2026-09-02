package ru.project.search;

import ru.project.student.Student;

public class RecordBookNumberRangeCriteria implements StudentSearchCriteria {
  private final int min;
  private final int max;

  public RecordBookNumberRangeCriteria(int min, int max) {
    if (min > max) {
      throw new IllegalArgumentException("Min record number must be <= max");
    }
    this.min = min;
    this.max = max;
  }

  @Override
  public boolean matches(Student student) {
    int number = student.getRecordBookNumber();
    return number >= min && number <= max;
  }
}
