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

public class FileDataSource implements DataSource{
    private String fileName;

    public FileDataSource(String fileName) {
        this.fileName = fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public MyList<Student> fill(int size) {
        Path path = Paths.get(fileName);
        try(var lines = Files.lines(path)){
            return lines.skip(1)
                    .limit(size)
                    .map(e -> StringParser.parseString(e))
                    .filter(e -> !(e == null))
                    .collect(Collector.of(MyList::new,
                            MyList::add,
                            (left, right) -> left.addAll(right)));
        }catch(FileNotFoundException e){
            System.out.println("File not found.");
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
