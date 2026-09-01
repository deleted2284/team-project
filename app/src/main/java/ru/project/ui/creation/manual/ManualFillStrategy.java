package ru.project.ui.creation.manual;

import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;
import ru.project.ui.creation.FillStrategy;

public class ManualFillStrategy implements FillStrategy {

  private final ManualFillSettings settings;

  public ManualFillStrategy(ManualFillSettings settings) {
    this.settings = settings;
  }

  @Override
  public MyList<Student> create() {
    MyList<Student> mainCollection = new MyLinkedList<>();

    MyList<Student> manualDataCollection = settings.getCollection();

    for (int i = 0; i < manualDataCollection.size(); i++) {
      mainCollection.add(manualDataCollection.get(i));
    }

    return mainCollection;
  }
}
