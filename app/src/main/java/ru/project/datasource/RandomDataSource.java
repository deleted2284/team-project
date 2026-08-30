package ru.project.datasource;

import ru.project.student.Student;
import ru.project.student.StudentBuilder;
import ru.project.collection.MyList;
import ru.project.collection.MyLinkedList;
import java.util.Random;

public class RandomDataSource implements  DataSource{
    private final int size;
    //private final String CHARS = "АБВГДЕЖХИКЛМНОПРСТУФХЦЧЩЭЮЯ";
    private int upperBounRecordBookNumber;

    public RandomDataSource(int size) {
        this.size = size;
    }

    public void setUpperBounRecordBookNumber(int upperBounRecordBookNumber) {
        this.upperBounRecordBookNumber = upperBounRecordBookNumber;
    }

    private String getRandomGroupNumber(Random random){
        char prefix = (char)(random.nextInt(26) + (int)'A');
        String result = prefix + String.format("%02d", random.nextInt(100));
        return result;
    }
    /*private String getRandomRecordBookNumber(Random random){
        StringBuilder buildRes = new StringBuilder(CHARS.charAt(random.nextInt(CHARS.length())));
        buildRes.append(String.format("%08d", random.nextInt(99999999)));
        return buildRes.toString();
    }*/

    @Override
    public MyList<Student> create() {
        if (upperBounRecordBookNumber < 0)
            throw new IllegalArgumentException("The upper bound is negative!!!");
        Random random = new Random();
        MyList<Student> result = new MyLinkedList<>();
        for (short i = 0; i<size; i++){
            result.add(new StudentBuilder()
                    .setGroupNumber(getRandomGroupNumber(random))
                    .setAverageGrade(Math.random() * 5.0)
                    .setRecordBookNumber(random.nextInt(upperBounRecordBookNumber + 1))
                    .build());
        }
        return result;
    }
}
