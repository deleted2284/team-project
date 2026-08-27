package ru.project.ui.window;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.collection.MyList;
import ru.project.model.AppState;
import ru.project.student.Student;
import ru.project.ui.base.BaseWindow;

public class CurrentCollectionWindow extends BaseWindow {

  public CurrentCollectionWindow(WindowBasedTextGUI gui, AppState state) {
    super("Текущая коллекция", gui);

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    MyList<Student> collection = state.getCollection();

    if (collection == null || collection.isEmpty()) {
      panel.addComponent(new Label("Текущая коллекция пуста."));
    } else {
      addStudents(panel, collection);
    }

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void addStudents(Panel panel, MyList<Student> collection) {
    for (int i = 0; i < collection.size(); i++) {
      Student student = collection.get(i);

      panel.addComponent(new Label((i + 1) + ". " + student));
    }
  }
}
