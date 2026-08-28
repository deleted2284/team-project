package ru.project.sorting;

import ru.project.student.Student;
import java.util.Comparator;

public class StudentGroupComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        return s1.getGroupNumber().compareTo(s2.getGroupNumber());
    }
}
