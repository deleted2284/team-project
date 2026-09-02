package ru.project.search;

import ru.project.student.Student;

public interface StudentSearchCriteria {
  boolean matches(Student student);
}
