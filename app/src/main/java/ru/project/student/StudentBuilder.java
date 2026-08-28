package ru.project.student;

public class StudentBuilder {
    private String groupNumber;
    private double averageGrade;
    private int recordBookNumber;

    public StudentBuilder setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
        return this;
    }

    public StudentBuilder setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
        return this;
    }

    public StudentBuilder setRecordBookNumber(int recordBookNumber) {
        this.recordBookNumber = recordBookNumber;
        return this;
    }

    public Student build() {
        return new Student(groupNumber, averageGrade, recordBookNumber);
    }
}
