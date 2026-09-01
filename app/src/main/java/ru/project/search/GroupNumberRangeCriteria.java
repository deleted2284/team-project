package ru.project.search;

import ru.project.student.Student;

public class GroupNumberRangeCriteria implements StudentSearchCriteria {
    private final String min;
    private final String max;

    public GroupNumberRangeCriteria(String min, String max) {
        if (min == null || max == null) {
            throw new IllegalArgumentException("Min and max must not be null");
        }
        if (min.compareTo(max) > 0) {
            throw new IllegalArgumentException("Min group must be <= max group");
        }
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean matches(Student student) {
        String group = student.getGroupNumber();
        return group.compareTo(min) >= 0 && group.compareTo(max) <= 0;
    }
}