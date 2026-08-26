package ru.project.presenter;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.collection.MyList;
import ru.project.model.AppState;
import ru.project.student.Student;
import ru.project.view.CollectionView;

public class CollectionPresenter extends BasePresenter {

  private final CollectionView view;

  public CollectionPresenter(AppState state, CollectionView view, WindowBasedTextGUI gui) {
    super(state, gui);
    this.view = view;
  }

  @Override
  public void start() {
    MyList<Student> collection = state.getCollection();

    if (collection == null || collection.isEmpty()) {
      view.showEmptyMessage();
    } else {
      view.showCollection(collection);
    }

    view.show(gui);
  }
}
