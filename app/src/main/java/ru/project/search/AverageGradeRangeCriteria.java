package ru.project.search;

import ru.project.student.Student;

public class AverageGradeRangeCriteria implements StudentSearchCriteria {
    private final double min;
    private final double max;

    public AverageGradeRangeCriteria(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("Min grade must be <= max grade");
        }
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean matches(Student student) {
        double grade = student.getAverageGrade();
        return grade >= min && grade <= max;
    }
}