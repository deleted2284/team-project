package ru.project.datasource;

import java.util.Scanner;
import ru.project.student.Student;
import ru.project.collection.MyList;

public class ManualDataSource implements DataSource{
    @Override
    public MyList<Student> fill(int size) {
        MyList<Student> result = new MyList<>();
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
