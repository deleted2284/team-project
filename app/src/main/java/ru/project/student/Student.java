package ru.project.student;

import java.util.Objects;

public class Student {
    private final String groupNumber;
    private final double averageGrade;
    private final String recordBookNumber;

    Student(String groupNumber, double averageGrade, String recordBookNumber) {
        if (groupNumber == null || groupNumber.isBlank()) {
            throw new IllegalArgumentException("Group number cannot be empty");
        }
        if (averageGrade < 0.0 || averageGrade > 5.0) {
            throw new IllegalArgumentException("Average grade must be between 0.0 and 5.0");
        }
        if (recordBookNumber == null || recordBookNumber.isBlank()) {
            throw new IllegalArgumentException("Record book number cannot be empty");
        }
        this.groupNumber = groupNumber;
        this.averageGrade = averageGrade;
        this.recordBookNumber = recordBookNumber;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public String getRecordBookNumber() {
        return recordBookNumber;
    }

    @Override
    public String toString() {
        return "Student{" +
                "groupNumber='" + groupNumber + '\'' +
                ", averageGrade=" + averageGrade +
                ", recordBookNumber='" + recordBookNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;

        }
        Student student = (Student) obj;

        return Double.compare(averageGrade, student.averageGrade) == 0
                && Objects.equals(groupNumber, student.groupNumber)
                && Objects.equals(recordBookNumber, student.recordBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, averageGrade, recordBookNumber);
    }
}