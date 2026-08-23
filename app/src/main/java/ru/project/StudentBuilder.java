public class StudentBuilder {
    private String groupNumber;
    private double averageGrade;
    private String recordBookNumber;

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public void setRecordBookNumber(String recordBookNumber) {
        this.recordBookNumber = recordBookNumber;
    }

    public Student build(){
        //TODO: Реализовать проверку на корректность введенных значений

        return new Student(groupNumber,averageGrade, recordBookNumber);
    }
}
