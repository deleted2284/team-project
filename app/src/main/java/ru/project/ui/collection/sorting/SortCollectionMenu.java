package ru.project.ui.collection.sorting;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.common.MessageWindow;

public class SortCollectionMenu extends BaseModalWindow {

  private final AppState state;

  private final Label sortMethodLabel;
  private final Label sortFieldLabel;

  private final SortMethodSelectionWindow sortMethodSelectionWindow;
  private final NormalSortSettingsWindow normalSortSettingsWindow;
  private final ParitySortSettingsWindow paritySortSettingsWindow;

  private SortMethod selectedSortMethod;
  private SortField selectedSortField;

  public SortCollectionMenu(WindowBasedTextGUI gui, AppState state) {

    super("Меню сортировки текущей коллекции", gui);

    this.state = state;
    this.selectedSortMethod = SortMethod.NORMAL;
    this.selectedSortField = SortField.GROUP_NUMBER;

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    this.sortMethodLabel = new Label("Способ сортировки: " + getSortMethodName(selectedSortMethod));

    this.sortFieldLabel =
        new Label("Сортировать по: " + getSortFieldName(selectedSortMethod, selectedSortField));

    this.sortMethodSelectionWindow =
        new SortMethodSelectionWindow(
            gui,
            sortMethod -> {
              selectedSortMethod = sortMethod;

              sortMethodLabel.setText(
                  "Способ сортировки: " + getSortMethodName(selectedSortMethod));

              sortFieldLabel.setText(
                  "Сортировать по: " + getSortFieldName(selectedSortMethod, selectedSortField));
            });

    this.normalSortSettingsWindow =
        new NormalSortSettingsWindow(
            gui,
            sortField -> {
              selectedSortField = sortField;

              sortFieldLabel.setText(
                  "Сортировать по: " + getSortFieldName(selectedSortMethod, selectedSortField));
            });

    this.paritySortSettingsWindow = new ParitySortSettingsWindow(gui);

    panel.addComponent(sortMethodLabel);
    panel.addComponent(sortFieldLabel);

    panel.addComponent(new Button("Выбрать способ сортировки", this::selectSortMethod));

    panel.addComponent(
        new Button("Настроить текущий способ сортировки", this::setupCurrentSortMethod));

    panel.addComponent(new Button("Сортировать текущую коллекцию", this::sortCurrentCollection));

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void setupCurrentSortMethod() {
    switch (selectedSortMethod) {
      case NORMAL -> normalSortSettingsWindow.showModal();

      case PARITY -> paritySortSettingsWindow.showModal();
    }
  }

  private void selectSortMethod() {
    sortMethodSelectionWindow.showModal();
  }

  private void sortCurrentCollection() {
    if (state.getMainCollection() == null || state.getMainCollection().isEmpty()) {

      MessageWindow.showModal(gui, "Текущая коллекция пуста.");

      return;
    }

    switch (selectedSortMethod) {
      case NORMAL -> sortNormally();

      case PARITY -> sortByParity();
    }
  }

  private void sortNormally() {
    // TODO
    MessageWindow.showModal(gui, "Обычная сортировка не реализована.");
  }

  private void sortByParity() {
    // TODO
    MessageWindow.showModal(gui, "Сортировка по чётности не реализована.");
  }

  private String getSortMethodName(SortMethod sortMethod) {
    return switch (sortMethod) {
      case NORMAL -> "Обычная сортировка";
      case PARITY -> "Сортировка по чётности";
    };
  }

  private String getSortFieldName(SortMethod sortMethod, SortField sortField) {
    return switch (sortMethod) {
      case NORMAL -> getNormalSortFieldName(sortField);
      case PARITY -> "номеру зачётной книжки";
    };
  }

  private String getNormalSortFieldName(SortField sortField) {
    return switch (sortField) {
      case GROUP_NUMBER -> "номеру группы";
      case AVERAGE_GRADE -> "среднему баллу";
      case RECORD_BOOK_NUMBER -> "номеру зачётной книжки";
    };
  }
}
