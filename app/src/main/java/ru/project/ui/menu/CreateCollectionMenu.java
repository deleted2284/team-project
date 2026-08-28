package ru.project.ui.menu;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;
import ru.project.model.FillMethod;
import ru.project.ui.base.BaseWindow;
import ru.project.ui.window.CustomDataSettingsWindow;
import ru.project.ui.window.InputValueWindow;
import ru.project.ui.window.MessageWindow;

public class CreateCollectionMenu extends BaseWindow {

  private int collectionSize;
  private FillMethod selectedFillMethod;

  private final Label sizeLabel;
  private final Label fillMethodLabel;

  private final AppState state;

  public CreateCollectionMenu(WindowBasedTextGUI gui, AppState state) {
    super("Создание новой коллекции", gui);

    this.state = state;

    collectionSize = 0;
    selectedFillMethod = FillMethod.CUSTOM;

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    sizeLabel = new Label("Размер: не задан");

    fillMethodLabel = new Label("Способ заполнения: " + getFillMethodName(selectedFillMethod));

    panel.addComponent(sizeLabel);
    panel.addComponent(fillMethodLabel);

    panel.addComponent(new Button("Задать размер", this::openCollectionSizeInputValueWindow));

    panel.addComponent(new Button("Выбрать способ заполнения", this::selectFillMethod));

    panel.addComponent(
        new Button("Настроить текущий способ заполнения", this::setupCurrentFillMethod));

    panel.addComponent(new Button("Создать новую коллекцию", this::createCollection));

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void openCollectionSizeInputValueWindow() {
    InputValueWindow window =
        new InputValueWindow(
            gui,
            "Размер коллекции",
            "Введите размер коллекции:",
            collectionSize > 0 ? String.valueOf(collectionSize) : null,
            this::handleSizeEntered);

    window.show();
  }

  private boolean handleSizeEntered(String value) {
    try {
      int enteredSize = Integer.parseInt(value);
      if (enteredSize <= 0) {
        MessageWindow.show(gui, "Размер должен быть больше 0.");
        return false;
      }
      collectionSize = enteredSize;
      sizeLabel.setText("Размер: " + collectionSize);
      return true;
    } catch (NumberFormatException e) {
      MessageWindow.show(gui, "Введите целое число.");
      return false;
    }
  }

  private void setupCurrentFillMethod() {
    switch (selectedFillMethod) {
      case CUSTOM -> {
        BaseWindow window = new CustomDataSettingsWindow(gui, state, collectionSize);

        window.show();
      }

      case FILE ->
          // TODO
          MessageWindow.show(gui, "Не реализовано.");

      case RANDOM ->
          MessageWindow.show(gui, "Для случайных данных дополнительные настройки не требуются.");
    }
  }

  private void selectFillMethod() {
    BaseWindow menu =
        new FillMethodSelectionWindow(
            gui,
            fillMethod -> {
              selectedFillMethod = fillMethod;
              fillMethodLabel.setText(
                  "Способ заполнения: " + getFillMethodName(selectedFillMethod));
            });

    menu.show();
  }

  private void createCollection() {
    if (collectionSize <= 0) {
      MessageWindow.show(gui, "Сначала задайте размер коллекции.");
      return;
    }

    switch (selectedFillMethod) {
      case CUSTOM:
        createCollectionFromCustomData();
        break;

      case RANDOM:
        createCollectionFromRandomData();
        break;

      case FILE:
        createCollectionFromFileData();
        break;
    }
  }

  private void createCollectionFromCustomData() {
    // TODO
    MessageWindow.show(gui, "Не реализовано.");
  }

  private void createCollectionFromRandomData() {
    // TODO
    MessageWindow.show(gui, "Не реализовано.");
  }

  private void createCollectionFromFileData() {
    // TODO
    MessageWindow.show(gui, "Не реализовано.");
  }

  private String getFillMethodName(FillMethod fillMethod) {
    return switch (fillMethod) {
      case CUSTOM -> "Пользовательские данные";

      case RANDOM -> "Случайные данные";

      case FILE -> "Данные из файла";
    };
  }
}
