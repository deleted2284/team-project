package ru.project.datasource;

import ru.project.student.Student;
import ru.project.student.StudentBuilder;
import ru.project.collection.MyList;

public class StringParser {
    public static Student parseString(String str){
        String [] parts = str.split(";");
        try{
            return new StudentBuilder()
                    .setGroupNumber(parts[0])
                    .setAverageGrade(Double.valueOf(parts[1]))
                    .setRecordBookNumber(parts[2])
                    .build();
        }catch(NumberFormatException e){
            return null;
        }
    }
}
