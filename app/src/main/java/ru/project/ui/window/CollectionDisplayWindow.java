package ru.project.ui.window;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import ru.project.collection.MyList;
import ru.project.student.Student;
import ru.project.ui.base.BaseModalWindow;

public class CollectionDisplayWindow extends BaseModalWindow {

  private final MyList<Student> collection;

  private TextBox collectionTextBox;
  private Button exitButton;

  public CollectionDisplayWindow(WindowBasedTextGUI gui, MyList<Student> collection) {

    super("Просмотр коллекции", gui);

    this.collection = collection;

    Panel panel = createPanel();

    setComponent(panel);

    exitButton.takeFocus();
  }

  @Override
  public boolean handleInput(KeyStroke key) {
    if (key.getKeyType() == KeyType.Escape && collectionTextBox.isFocused()) {

      exitButton.takeFocus();
      return true;
    }

    return super.handleInput(key);
  }

  private Panel createPanel() {
    Panel panel = new Panel();

    panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

    addCollectionDisplay(panel);
    addExitButton(panel);

    return panel;
  }

  private void addCollectionDisplay(Panel panel) {
    collectionTextBox = createCollectionScrollBox();

    panel.addComponent(collectionTextBox);
  }

  private TextBox createCollectionScrollBox() {
    TextBox textBox = new TextBox(new TerminalSize(80, 20), TextBox.Style.MULTI_LINE);

    textBox.setReadOnly(true);

    if (collection == null || collection.isEmpty()) {
      textBox.setText("Коллекция пуста.");
    } else {
      textBox.setText(buildCollectionText());
    }

    return textBox;
  }

  private void addExitButton(Panel panel) {
    panel.addComponent(new EmptySpace(new TerminalSize(0, 1)));

    exitButton = new Button("Выйти", this::close);

    panel.addComponent(exitButton);
  }

  private String buildCollectionText() {
    StringBuilder text = new StringBuilder();

    for (int i = 0; i < collection.size(); i++) {
      Student student = collection.get(i);

      text.append("[")
          .append(i)
          .append("] ")
          .append(student == null ? "null" : student)
          .append("\n");
    }

    return text.toString();
  }
}
