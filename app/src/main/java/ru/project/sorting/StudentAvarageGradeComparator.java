package ru.project.sorting;

import ru.project.model.Student;
import java.util.Comparator;

public class StudentAvarageGradeComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        return Double.compare(s1.getAvarageGrade(), s2.getAvarageGrade());
    }
}