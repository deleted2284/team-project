package ru.project.sorting;

import ru.project.student.Student;
import java.util.Comparator;

public class StudentRecordBookComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        return Integer.compare(s1.getRecordBookNumber(), s2.getRecordBookNumber());
    }
}