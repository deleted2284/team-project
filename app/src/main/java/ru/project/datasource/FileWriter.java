package ru.project.datasource;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import ru.project.student.Student;
import ru.project.collection.MyList;
import ru.project.collection.MyLinkedList;
//import ru.project.collection.Iterator;

public class FileWriter {
    private Path path;

    public FileWriter(String absolutePath) {
        this.path = Path.of(absolutePath);
    }

    public void write(MyList<Student> iist){
        //int sizeOfCollection = list.size();
        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            /*Iterator iterator = list.getIterator();
            while (iterator.hasNext()){
                writer.write(iterator.next().toFileString());
                writer.newLine();
            }*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
