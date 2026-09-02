package ru.project.search;

import java.util.ArrayList;
import java.util.List;
import ru.project.student.Student;

public class CompositeStudentSearchCriteria implements StudentSearchCriteria {
  private final SearchOperation operation;
  private final List<StudentSearchCriteria> criteriaList = new ArrayList<>();

  public CompositeStudentSearchCriteria(SearchOperation operation) {
    if (operation == null) {
      throw new IllegalArgumentException("Operation must not be null");
    }
    this.operation = operation;
  }

  public void add(StudentSearchCriteria criteria) {
    if (criteria == null) {
      throw new IllegalArgumentException("Criteria must not be null");
    }
    criteriaList.add(criteria);
  }

  public void remove(StudentSearchCriteria criteria) {
    criteriaList.remove(criteria);
  }

  @Override
  public boolean matches(Student student) {
    if (criteriaList.isEmpty()) {
      return operation == SearchOperation.INTERSECTION;
    }

    if (operation == SearchOperation.UNION) {
      for (StudentSearchCriteria c : criteriaList) {
        if (c.matches(student)) {
          return true;
        }
      }
      return false;
    } else {
      for (StudentSearchCriteria c : criteriaList) {
        if (!c.matches(student)) {
          return false;
        }
      }
      return true;
    }
  }
}
