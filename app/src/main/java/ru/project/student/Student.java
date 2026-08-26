package ru.project.student;

import java.util.Objects;

public class Student {
    private final String groupNumber;
    private final double averageGrade;
    private final int recordBookNumber;

    // Конструктор с проверками
    public Student(String groupNumber, double averageGrade, int recordBookNumber) {
        // Проверка groupNumber на формат
        if (groupNumber == null || !groupNumber.matches("^[A-Z][0-9]{2}$")) {
            throw new IllegalArgumentException("Group number must match format: one uppercase letter + two digits (e.g. A12)");
        }
        if (averageGrade < 0.0 || averageGrade > 5.0) {
            throw new IllegalArgumentException("Average grade must be between 0.0 and 5.0");
        }
        if (recordBookNumber <= 0) {
            throw new IllegalArgumentException("Record book number must be positive");
        }
        this.groupNumber = groupNumber;
        this.averageGrade = averageGrade;
        this.recordBookNumber = recordBookNumber;
    }

    // Геттеры — исправлен тип groupNumber (теперь String)
    public String getGroupNumber() {
        return groupNumber;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public int getRecordBookNumber() {
        return recordBookNumber;
    }

    // Методы копирования (with-методы)
    public Student withGroupNumber(String groupNumber) {
        return new Student(groupNumber, this.averageGrade, this.recordBookNumber);
    }

    public Student withAverageGrade(double averageGrade) {
        return new Student(this.groupNumber, averageGrade, this.recordBookNumber);
    }

    public Student withRecordBookNumber(int recordBookNumber) {
        return new Student(this.groupNumber, this.averageGrade, recordBookNumber);
    }

    @Override
    public String toString() {
        return "Student{" +
                "groupNumber='" + groupNumber + '\'' +
                ", averageGrade=" + averageGrade +
                ", recordBookNumber=" + recordBookNumber +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return Double.compare(averageGrade, student.averageGrade) == 0
                && recordBookNumber == student.recordBookNumber
                && Objects.equals(groupNumber, student.groupNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupNumber, averageGrade, recordBookNumber);
    }
}
