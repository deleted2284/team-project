package ru.project.ui.menu;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;
import ru.project.model.SortMethod;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.window.MessageWindow;
import ru.project.ui.window.SortMethodSelectionWindow;

public class SortCollectionMenu extends BaseModalWindow {

  private final AppState state;

  private final Label sortMethodLabel;
  private final SortMethodSelectionWindow sortMethodSelectionWindow;

  private SortMethod selectedSortMethod;

  public SortCollectionMenu(WindowBasedTextGUI gui, AppState state) {

    super("Меню сортировки текущей коллекции", gui);

    this.state = state;
    this.selectedSortMethod = SortMethod.NORMAL;

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    this.sortMethodLabel = new Label("Способ сортировки: " + getSortMethodName(selectedSortMethod));

    this.sortMethodSelectionWindow =
        new SortMethodSelectionWindow(
            gui,
            sortMethod -> {
              selectedSortMethod = sortMethod;

              sortMethodLabel.setText(
                  "Способ сортировки: " + getSortMethodName(selectedSortMethod));
            });

    panel.addComponent(sortMethodLabel);

    panel.addComponent(new Button("Выбрать способ сортировки", this::selectSortMethod));

    panel.addComponent(
        new Button("Настроить текущий способ сортировки", this::setupCurrentSortMethod));

    panel.addComponent(new Button("Сортировать текущую коллекцию", this::sortCurrentCollection));

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void setupCurrentSortMethod() {
    MessageWindow.showModal(gui, "Настройка способа сортировки пока не реализована.");
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
      case NORMAL:
        sortNormally();
        break;

      case PARITY:
        sortByParity();
        break;
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
}
