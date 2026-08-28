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
    private String fileName;

    public FileDataSource(String fileName) {
        this.fileName = fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public MyList<Student> create() {
        Path path = Paths.of(fileName);
        try(var lines = Files.lines(path)){
            return lines
                    //.skip(1)
                    .map(e -> StringParser.parseString(e))
                    .filter(e -> !(e == null))
                    .collect(Collector.of(MyLinkedList::new,
                            MyLinkedList::add,
                            (left, right) -> left;
        }catch(FileNotFoundException e){
            System.out.println("File not found.");
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
