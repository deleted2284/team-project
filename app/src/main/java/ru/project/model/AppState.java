package ru.project.model;

import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;

public class AppState {

  private MyList<Student> collection;
  private String currentFilePath;

  public AppState() {
    this.collection = new MyLinkedList<>();
    this.currentFilePath = null;
  }

  public MyList<Student> getCollection() {
    return collection;
  }

  public void setCollection(MyList<Student> collection) {
    this.collection = collection;
  }

  public String getCurrentFilePath() {
    return currentFilePath;
  }

  public void setCurrentFilePath(String currentFilePath) {
    this.currentFilePath = currentFilePath;
  }
}
