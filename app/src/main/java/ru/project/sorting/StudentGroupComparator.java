package ru.project.sorting;

import java.util.Comparator;
import ru.project.student.Student;

public class StudentGroupComparator implements Comparator<Student> {

  @Override
  public int compare(Student s1, Student s2) {
    return s1.getGroupNumber().compareTo(s2.getGroupNumber());
  }
}
