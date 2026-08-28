package ru.project.model;

import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;

public class AppState {

  private MyList<Student> currentCollection;
  private MyList<Student> customDataCollection;
  private String currentFilePath;

  public AppState() {
    this.currentCollection = new MyLinkedList<>();
    this.customDataCollection = new MyLinkedList<>();
    this.currentFilePath = null;
  }

  public MyList<Student> getCurrentCollection() {
    return currentCollection;
  }

  public void setCurrentCollection(MyList<Student> currentCollection) {
    this.currentCollection = currentCollection;
  }

  public MyList<Student> getCustomDataCollection() {
    return customDataCollection;
  }

  public void setCustomDataCollection(MyList<Student> customDataCollection) {

    this.customDataCollection = customDataCollection;
  }

  public String getCurrentFilePath() {
    return currentFilePath;
  }

  public void setCurrentFilePath(String currentFilePath) {
    this.currentFilePath = currentFilePath;
  }
}
