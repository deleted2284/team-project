package ru.project.ui.common;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Consumer;

public class CollectionSizeInputWindow {

  private final WindowBasedTextGUI gui;
  private final Integer currentSize;
  private final Consumer<Integer> onSizeEntered;

  public CollectionSizeInputWindow(
      WindowBasedTextGUI gui, Integer currentSize, Consumer<Integer> onSizeEntered) {

    this.gui = gui;
    this.currentSize = currentSize;
    this.onSizeEntered = onSizeEntered;
  }

  public void showModal() {
    InputValueWindow window =
        new InputValueWindow(
            gui,
            "Размер коллекции",
            "Введите размер коллекции:",
            currentSize != null && currentSize > 0 ? String.valueOf(currentSize) : null,
            this::handleSizeEntered);

    window.showModal();
  }

  private boolean handleSizeEntered(String value) {
    try {
      int size = Integer.parseInt(value);

      if (size <= 0) {
        MessageWindow.showModal(gui, "Размер должен быть больше 0.");

        return false;
      }

      onSizeEntered.accept(size);
      return true;

    } catch (NumberFormatException e) {
      MessageWindow.showModal(gui, "Введите целое число.");

      return false;
    }
  }
}
