package ru.project.datasource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collector;
import ru.project.student.Student;
import ru.project.collection.MyList;
import ru.project.collection.MyLinkedList;

public class FileDataSource implements DataSource{
    private final String fileName;

    public FileDataSource(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public MyList<Student> create() {
        MyList<Student> resultList = new MyLinkedList<>();
        try {
            StudentCsvReader reader = new StudentCsvReader(fileName);
            while reader.hasNext() {
                result.append(reader.next());
            }
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return null;
        }
        catch (NoSuchElementException e){
            System.out.println("No such element");
            return null;
        }
    }
}
