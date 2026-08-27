package ru.project.sorting;

import ru.project.student.Student;
import java.util.Comparator;

public class StudentAverageGradeComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        return Double.compare(s1.getAverageGrade(), s2.getAverageGrade());
    }
}