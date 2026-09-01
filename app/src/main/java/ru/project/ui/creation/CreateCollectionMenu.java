package ru.project.ui.creation;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.model.AppState;
import ru.project.student.Student;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.common.InputValueWindow;
import ru.project.ui.common.MessageWindow;

public class CreateCollectionMenu extends BaseModalWindow {

  private int collectionSize;
  private FillMethod selectedFillMethod;

  private final Label sizeLabel;
  private final Label fillMethodLabel;

  private final CustomDataSettingsWindow customDataSettingsWindow;

  public CreateCollectionMenu(WindowBasedTextGUI gui, AppState state) {
    super("Создание новой коллекции", gui);

    collectionSize = 0;
    selectedFillMethod = FillMethod.CUSTOM;

    customDataSettingsWindow = new CustomDataSettingsWindow(gui, state, () -> collectionSize);

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    sizeLabel = new Label("Размер: не задан");

    fillMethodLabel = new Label("Способ заполнения: " + getFillMethodName(selectedFillMethod));

    panel.addComponent(sizeLabel);
    panel.addComponent(fillMethodLabel);

    panel.addComponent(new Button("Задать размер", this::showCollectionSizeInputValueWindow));

    panel.addComponent(new Button("Выбрать способ заполнения", this::selectFillMethod));

    panel.addComponent(
        new Button("Настроить текущий способ заполнения", this::setupCurrentFillMethod));

    panel.addComponent(new Button("Создать новую коллекцию", () -> updateMainCollection(state)));

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void showCollectionSizeInputValueWindow() {
    InputValueWindow window =
        new InputValueWindow(
            gui,
            "Размер коллекции",
            "Введите размер коллекции:",
            collectionSize > 0 ? String.valueOf(collectionSize) : null,
            this::handleSizeEntered);

    window.showModal();
  }

  private boolean handleSizeEntered(String value) {
    try {
      int enteredSize = Integer.parseInt(value);
      if (enteredSize <= 0) {
        MessageWindow.showModal(gui, "Размер должен быть больше 0.");
        return false;
      }
      collectionSize = enteredSize;
      sizeLabel.setText("Размер: " + collectionSize);
      return true;
    } catch (NumberFormatException e) {
      MessageWindow.showModal(gui, "Введите целое число.");
      return false;
    }
  }

  private void setupCurrentFillMethod() {
    switch (selectedFillMethod) {
      case CUSTOM -> {
        customDataSettingsWindow.showModal();
      }

      case FILE ->
          // TODO
          MessageWindow.showModal(gui, "Не реализовано.");

      case RANDOM ->
          MessageWindow.showModal(
              gui, "Для случайных данных дополнительные настройки не требуются.");
    }
  }

  private void selectFillMethod() {
    BaseModalWindow menu =
        new FillMethodSelectionWindow(
            gui,
            fillMethod -> {
              selectedFillMethod = fillMethod;
              fillMethodLabel.setText(
                  "Способ заполнения: " + getFillMethodName(selectedFillMethod));
            });

    menu.showModal();
  }

  private void updateMainCollection(AppState state) {
    if (collectionSize <= 0) {
      MessageWindow.showModal(gui, "Сначала задайте размер коллекции.");
      return;
    }

    switch (selectedFillMethod) {
      case CUSTOM:
        createCollectionFromCustomData(state);
        break;

      case RANDOM:
        createCollectionFromRandomData(state);
        break;

      case FILE:
        createCollectionFromFileData(state);
        break;
    }
  }

  private void createCollectionFromCustomData(AppState state) {
    MyList<Student> customDataCollection = state.getCustomDataCollection();

    MyList<Student> mainCollection = new MyLinkedList<>();

    for (int i = 0; i < customDataCollection.size(); i++) {
      mainCollection.add(customDataCollection.get(i));
    }

    state.setMainCollection(mainCollection);

    MessageWindow.showModal(gui, "Коллекция успешно создана.");
  }

  private void createCollectionFromRandomData(AppState state) {
    // TODO
    MessageWindow.showModal(gui, "Не реализовано.");
  }

  private void createCollectionFromFileData(AppState state) {
    // TODO
    MessageWindow.showModal(gui, "Не реализовано.");
  }

  private String getFillMethodName(FillMethod fillMethod) {
    return switch (fillMethod) {
      case CUSTOM -> "Пользовательские данные";

      case RANDOM -> "Случайные данные";

      case FILE -> "Данные из файла";
    };
  }
}
