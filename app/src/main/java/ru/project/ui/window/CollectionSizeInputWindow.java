package ru.project.ui.window;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.IntConsumer;
import ru.project.ui.base.BaseWindow;

public class CollectionSizeInputWindow extends BaseWindow {

  private final TextBox sizeTextBox;
  private final IntConsumer onSizeEntered;

  public CollectionSizeInputWindow(WindowBasedTextGUI gui, IntConsumer onSizeEntered) {

    super("Задать размер", gui);

    this.onSizeEntered = onSizeEntered;

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    panel.addComponent(new Label("Введите размер:"));

    sizeTextBox = new TextBox();
    panel.addComponent(sizeTextBox);

    panel.addComponent(new Button("Подтвердить", this::handleSizeEntered));

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void handleSizeEntered() {
    try {
      int size = Integer.parseInt(sizeTextBox.getText().trim());

      if (size <= 0) {
        MessageWindow.create(gui, "Размер должен быть больше 0.");
        return;
      }

      close();
      onSizeEntered.accept(size);

    } catch (NumberFormatException e) {
      MessageWindow.create(gui, "Введите целое число.");
    }
  }
}
