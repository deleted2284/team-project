package ru.project.ui.menu;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;
import ru.project.model.FillMethod;
import ru.project.ui.base.BaseWindow;
import ru.project.ui.window.CollectionSizeInputWindow;
import ru.project.ui.window.MessageWindow;

public class CreateCollectionMenu extends BaseWindow {

  private int collectionSize;
  private FillMethod selectedFillMethod;

  private final Label sizeLabel;
  private final Label fillMethodLabel;

  public CreateCollectionMenu(WindowBasedTextGUI gui, AppState state) {
    super("Создание новой коллекции", gui);

    collectionSize = 0;
    selectedFillMethod = FillMethod.CUSTOM;

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    sizeLabel = new Label("Размер: не задан");

    fillMethodLabel = new Label("Способ заполнения: " + getFillMethodName(selectedFillMethod));

    panel.addComponent(sizeLabel);
    panel.addComponent(fillMethodLabel);

    panel.addComponent(new Button("Задать размер", this::openCollectionSizeInputWindow));

    panel.addComponent(new Button("Настроить способ заполнения", this::openFillMethodMenu));

    panel.addComponent(new Button("Создать новую коллекцию", this::createCollection));

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void openCollectionSizeInputWindow() {
    CollectionSizeInputWindow window =
        new CollectionSizeInputWindow(
            gui,
            size -> {
              collectionSize = size;
              sizeLabel.setText("Размер: " + collectionSize);
            });

    window.show();
  }

  private void openFillMethodMenu() {
    FillMethodSelectionWindow menu =
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
      MessageWindow.create(gui, "Сначала задайте размер коллекции.");
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
    MessageWindow.create(gui, "Не реализовано.");
  }

  private void createCollectionFromRandomData() {
    // TODO
    MessageWindow.create(gui, "Не реализовано.");
  }

  private void createCollectionFromFileData() {
    // TODO
    MessageWindow.create(gui, "Не реализовано.");
  }

  private String getFillMethodName(FillMethod fillMethod) {
    return switch (fillMethod) {
      case CUSTOM -> "Пользовательские данные";

      case RANDOM -> "Случайные данные";

      case FILE -> "Данные из файла";
    };
  }
}
