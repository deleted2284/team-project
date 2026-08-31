package ru.project.search;

import ru.project.list.MyList;
import ru.project.list.MyLinkedList;
import ru.project.student.Student;

public class StudentSearchService {
    private final StudentSearchCriteria criteria;

    public StudentSearchService(StudentSearchCriteria criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("Criteria must not be null");
        }
        this.criteria = criteria;
    }

    public MyList<Student> find(MyList<Student> students) {
        if (students == null) {
            return new MyLinkedList<>();
        }
        MyList<Student> result = new MyLinkedList<>();
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            if (criteria.matches(s)) {
                result.add(s);
            }
        }
        return result;
    }
}