package ru.project.view;

import ru.project.collection.MyList;
import ru.project.student.Student;

public class CollectionView extends PanelView {

  public CollectionView() {
    super("Текущая коллекция");
  }

  public void showCollection(MyList<Student> collection) {
    for (int i = 0; i < collection.size(); i++) {
      Student student = collection.get(i);

      addLabel((i + 1) + ". " + student);
    }

    addButton("Выйти", this::close);
  }

  public void showEmptyMessage() {
    addLabel("Текущая коллекция пуста.");

    addButton("Выйти", this::close);
  }
}
