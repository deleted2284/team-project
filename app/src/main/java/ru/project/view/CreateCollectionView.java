package ru.project.view;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.IntConsumer;
import ru.project.model.FillMethod;

public class CreateCollectionView extends PanelView {

  private final Label sizeLabel;
  private final Label fillMethodLabel;

  private Runnable setSizeAction = this::defaultAction;
  private Runnable configureFillMethodAction = this::defaultAction;
  private Runnable createCollectionAction = this::defaultAction;

  public CreateCollectionView() {
    super("Создание новой коллекции");

    sizeLabel = addLabel("Размер: не задан");

    fillMethodLabel = addLabel("Способ заполнения: Пользовательские данные");

    addButton("Задать размер", () -> setSizeAction.run());

    addButton("Настроить способ заполнения", () -> configureFillMethodAction.run());

    addButton("Создать новую коллекцию", () -> createCollectionAction.run());

    addButton("Выйти", this::close);
  }

  public void setSetSizeAction(Runnable action) {
    setSizeAction = action;
  }

  public void setConfigureFillMethodAction(Runnable action) {
    configureFillMethodAction = action;
  }

  public void setCreateCollectionAction(Runnable action) {
    createCollectionAction = action;
  }

  public void updateSize(int size) {
    sizeLabel.setText("Размер: " + size);
  }

  public void updateFillMethod(FillMethod fillMethod) {
    fillMethodLabel.setText("Способ заполнения: " + getFillMethodName(fillMethod));
  }

  public void showMessage(WindowBasedTextGUI gui, String message) {
    PanelView messageView = new PanelView("Информация");

    messageView.addLabel(message);

    messageView.addButton("OK", messageView::close);

    messageView.show(gui);
  }

  public void showSetSizeDialog(WindowBasedTextGUI gui, IntConsumer onSizeEntered) {
    PanelView dialog = new PanelView("Задать размер");

    dialog.addLabel("Введите размер:");

    TextBox textBox = dialog.addTextBox();

    dialog.addButton("Подтвердить", () -> handleSizeInput(gui, dialog, textBox, onSizeEntered));

    dialog.addButton("Вернуться", dialog::close);

    dialog.show(gui);
  }

  private void handleSizeInput(
      WindowBasedTextGUI gui, PanelView dialog, TextBox textBox, IntConsumer onSizeEntered) {
    try {
      int size = Integer.parseInt(textBox.getText().trim());

      dialog.close();
      onSizeEntered.accept(size);

    } catch (NumberFormatException e) {
      showMessage(gui, "Введите целое число.");
    }
  }

  private String getFillMethodName(FillMethod fillMethod) {
    return switch (fillMethod) {
      case CUSTOM -> "Пользовательские данные";
      case RANDOM -> "Случайные данные";
      case FILE -> "Данные из файла";
    };
  }
}
