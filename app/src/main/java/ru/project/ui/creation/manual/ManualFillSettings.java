package ru.project.ui.creation.manual;

import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;

public class ManualFillSettings {

  private MyList<Student> manualDataCollection;

  public ManualFillSettings() {
    this.manualDataCollection = new MyLinkedList<>();
  }

  public MyList<Student> getCollection() {
    return manualDataCollection;
  }

  public void setCollection(MyList<Student> collection) {
    this.manualDataCollection = collection;
  }
}
