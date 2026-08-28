package ru.project.ui.window;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Predicate;
import ru.project.ui.base.BaseWindow;

public class InputValueWindow extends BaseWindow {

  private final TextBox textBox;
  private final Predicate<String> onSave;

  public InputValueWindow(
      WindowBasedTextGUI gui,
      String title,
      String prompt,
      String initialValue,
      Predicate<String> onSave) {

    super(title, gui);

    this.onSave = onSave;

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    panel.addComponent(new Label(prompt));

    textBox = new TextBox(initialValue == null ? "" : initialValue);

    panel.addComponent(textBox);

    panel.addComponent(new Button("Сохранить", this::save));

    panel.addComponent(new Button("Отмена", this::close));

    setComponent(panel);
  }

  private void save() {
    String value = textBox.getText().trim();

    if (value.isEmpty()) {
      MessageWindow.show(gui, "Значение не может быть пустым.");
      return;
    }

    if (onSave.test(value)) {
      close();
    }
  }
}
