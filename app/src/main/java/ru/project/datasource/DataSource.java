package ru.project.datasource;

import ru.project.student.Student;
import ru.project.collection.MyList;


public interface DataSource {
    MyList<Student> fill(int size);
}
