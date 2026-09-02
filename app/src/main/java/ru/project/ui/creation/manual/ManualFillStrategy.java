package ru.project.ui.creation.manual;

import ru.project.collection.MyList;
import ru.project.collectors.MyCollectors;
import ru.project.student.Student;
import ru.project.ui.creation.FillStrategy;

public class ManualFillStrategy implements FillStrategy {

  private final ManualFillSettings settings;

  public ManualFillStrategy(ManualFillSettings settings) {
    this.settings = settings;
  }

  @Override
  public MyList<Student> create() {
    return settings.getCollection().stream().collect(MyCollectors.toMyList());
  }
}
