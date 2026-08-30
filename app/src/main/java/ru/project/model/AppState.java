package ru.project.model;

import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;

public class AppState {

  private MyList<Student> mainCollection;

  public AppState() {
    this.mainCollection = new MyLinkedList<>();
  }

  public MyList<Student> getMainCollection() {
    return mainCollection;
  }

  public void setMainCollection(MyList<Student> currentCollection) {
    this.mainCollection = currentCollection;
  }
}
