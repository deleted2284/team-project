package ru.project.view;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.collection.MyList;
import ru.project.student.Student;

public class CollectionView {

  public void showCollection(WindowBasedTextGUI gui, MyList<Student> collection) {
    BasicWindow window = new BasicWindow("Текущая коллекция");

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    for (int i = 0; i < collection.size(); i++) {
      Student student = collection.get(i);

      panel.addComponent(new Label((i + 1) + ". " + student));
    }

    panel.addComponent(new Button("Вернуться", window::close));

    window.setComponent(panel);

    gui.addWindowAndWait(window);
  }

  public void showEmptyMessage(WindowBasedTextGUI gui) {
    BasicWindow window = new BasicWindow("Текущая коллекция");

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    panel.addComponent(new Label("Текущая коллекция пуста."));

    panel.addComponent(new Button("Вернуться", window::close));

    window.setComponent(panel);

    gui.addWindowAndWait(window);
  }
}
