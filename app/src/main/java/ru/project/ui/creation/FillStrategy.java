package ru.project.ui.creation;

import ru.project.collection.MyList;
import ru.project.student.Student;

public interface FillStrategy {

  MyList<Student> create();
}
