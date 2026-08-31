package ru.project.ui.creation;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.common.MessageWindow;
import ru.project.ui.creation.file.FileDataSettingsWindow;
import ru.project.ui.creation.file.FileFillSettings;
import ru.project.ui.creation.file.FileFillStrategy;
import ru.project.ui.creation.manual.ManualDataSettingsWindow;
import ru.project.ui.creation.manual.ManualFillSettings;
import ru.project.ui.creation.manual.ManualFillStrategy;
import ru.project.ui.creation.random.RandomDataSettingsWindow;
import ru.project.ui.creation.random.RandomFillSettings;
import ru.project.ui.creation.random.RandomFillStrategy;

public class CreateCollectionMenu extends BaseModalWindow {

  private final AppState state;

  private FillMethod selectedFillMethod;
  private FillStrategy fillStrategy;

  private final Label fillMethodLabel;

  private final ManualDataSettingsWindow manualDataSettingsWindow;
  private final FileDataSettingsWindow fileDataSettingsWindow;
  private final RandomDataSettingsWindow randomDataSettingsWindow;

  public CreateCollectionMenu(WindowBasedTextGUI gui, AppState state) {

    super("Создание новой коллекции", gui);

    this.state = state;
    this.selectedFillMethod = FillMethod.CUSTOM;
    this.fillStrategy = null;

    this.manualDataSettingsWindow = new ManualDataSettingsWindow(gui, this::setManualFillStrategy);
    this.fileDataSettingsWindow = new FileDataSettingsWindow(gui, this::setFileFillStrategy);
    this.randomDataSettingsWindow = new RandomDataSettingsWindow(gui, this::setRandomFillStrategy);

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    fillMethodLabel = new Label("Способ заполнения: " + getFillMethodName(selectedFillMethod));

    panel.addComponent(fillMethodLabel);

    panel.addComponent(new Button("Выбрать способ заполнения", this::selectFillMethod));

    panel.addComponent(
        new Button("Настроить текущий способ заполнения", this::setupCurrentFillMethod));

    panel.addComponent(new Button("Создать новую коллекцию", this::updateMainCollection));

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void setManualFillStrategy(ManualFillSettings settings) {
    fillStrategy = new ManualFillStrategy(settings);
  }

  private void setFileFillStrategy(FileFillSettings settings) {
    fillStrategy = new FileFillStrategy(settings);
  }

  private void setRandomFillStrategy(RandomFillSettings settings) {
    fillStrategy = new RandomFillStrategy(settings);
  }

  private void setupCurrentFillMethod() {
    switch (selectedFillMethod) {
      case CUSTOM -> manualDataSettingsWindow.showModal();

      case FILE -> fileDataSettingsWindow.showModal();

      case RANDOM -> randomDataSettingsWindow.showModal();
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

  private void updateMainCollection() {
    if (fillStrategy == null) {
      MessageWindow.showModal(gui, "Сначала настройте способ заполнения.");

      return;
    }

    state.setMainCollection(fillStrategy.create());

    MessageWindow.showModal(gui, "Коллекция успешно создана.");
  }

  private String getFillMethodName(FillMethod fillMethod) {
    return switch (fillMethod) {
      case CUSTOM -> "Пользовательские данные";
      case RANDOM -> "Случайные данные";
      case FILE -> "Данные из файла";
    };
  }
}
