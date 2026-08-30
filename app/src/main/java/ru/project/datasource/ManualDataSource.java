package ru.project.datasource;

import java.util.Scanner;
import ru.project.student.Student;
import ru.project.collection.MyList;
import ru.project.collection.MyLinkedList;

public class ManualDataSource implements DataSource{
    private int size;
    private MyList<StudentBuffer> bufferMyList = new MyLinkedList<>();

    public ManualDataSource(int size) {
        this.size = size;
    }

    public boolean setStudent(
            int index,
            String groupNumber,
            double averageGrade,
            int recordBookNumber
    ){
        return true;
    }
    @Override
    public MyList<Student> create() {
        MyList<Student> result = new MyLinkedList<>();
        String strToParse;
        Student student;
        Scanner scanner = new Scanner(System.in);
        for (short i = 0; i < size; i++){
            strToParse = scanner.next();
            if (strToParse.equals("0"))
                break;
            student = StringParser.parseString(strToParse);
            if (student == null){
                System.out.println("Oops! Invalid input");
            }
            else{
                result.add(student);
            }
        }
        scanner.close();
        return null;
    }
}
