package ru.project.ui.collection;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.collection.sorting.SortCollectionMenu;
import ru.project.ui.common.CollectionDisplayWindow;
import ru.project.ui.common.CollectionFileSaveWindow;
import ru.project.ui.common.MessageWindow;

public class CurrentCollectionMenu extends BaseModalWindow {

  private final AppState state;

  private final SortCollectionMenu sortCollectionMenu;

  public CurrentCollectionMenu(WindowBasedTextGUI gui, AppState state) {

    super("Меню текущей коллекции", gui);

    this.state = state;

    this.sortCollectionMenu = new SortCollectionMenu(gui, state);

    ActionListBox menu = new ActionListBox();

    menu.addItem("Показать коллекцию", this::showCurrentCollection);

    menu.addItem("Сохранить коллекцию в файл", this::saveCurrentCollection);

    menu.addItem("Сортировать коллекцию", sortCollectionMenu::showModal);

    menu.addItem("Поиск элемента в коллекции", this::searchCollection);

    menu.addItem("Подсчёт количества вхождений элемента", this::countOccurrences);

    menu.addItem("Выйти", this::close);

    setComponent(menu);
  }

  private void showCurrentCollection() {
    CollectionDisplayWindow window = new CollectionDisplayWindow(gui, state.getMainCollection());

    window.showModal();
  }

  private void saveCurrentCollection() {
    CollectionFileSaveWindow window = new CollectionFileSaveWindow(gui, state.getMainCollection());

    window.showModal();
  }

  private void searchCollection() {
    // TODO
    MessageWindow.showModal(gui, "Не реализовано.");
  }

  private void countOccurrences() {
    // TODO
    MessageWindow.showModal(gui, "Не реализовано.");
  }
}
