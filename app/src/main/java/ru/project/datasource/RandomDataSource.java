package ru.project.datasource;

import ru.project.student.Student;
import ru.project.student.StudentBuilder;
import ru.project.collection.MyList;
import java.util.Random;

public class RandomDataSource implements  DataSource{
    private final String CHARS = "АБВГДЕЖХИКЛМНОПРСТУФХЦЧЩЭЮЯ";

    private String getRandomGroupNumber(Random random){
        StringBuilder buildRes = new StringBuilder(CHARS.charAt(random.nextInt(CHARS.length())));
        buildRes.append('-');
        buildRes.append(String.format("%03d", random.nextInt(999)));
        return buildRes.toString();
    }
    private String getRandomRecordBookNumber(Random random){
        StringBuilder buildRes = new StringBuilder(CHARS.charAt(random.nextInt(CHARS.length())));
        buildRes.append(String.format("%08d", random.nextInt(99999999)));
        return buildRes.toString();
    }

    @Override
    public MyList<Student> fill(int size) {
        Random random = new Random();
        MyList<Student> result = new MyList<>();
        for (short i = 0; i<size; i++){
            result.add(new StudentBuilder()
                    .setGroupNumber(getRandomGroupNumber(random))
                    .setAverageGrade(Math.random() * 5.0)
                    .setRecordBookNumber(getRandomRecordBookNumber(random))
                    .build());
        }
        return null;
    }
}
