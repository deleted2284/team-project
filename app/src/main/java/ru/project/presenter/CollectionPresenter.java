package ru.project.presenter;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.collection.MyList;
import ru.project.model.AppState;
import ru.project.student.Student;
import ru.project.view.CollectionView;

public class CollectionPresenter {

  private final AppState state;
  private final CollectionView view;
  private final WindowBasedTextGUI gui;

  public CollectionPresenter(AppState state, CollectionView view, WindowBasedTextGUI gui) {
    this.state = state;
    this.view = view;
    this.gui = gui;
  }

  public void start() {
    MyList<Student> collection = state.getCollection();

    if (collection == null || collection.isEmpty()) {
      view.showEmptyMessage(gui);
      return;
    }

    view.showCollection(gui, collection);
  }
}
