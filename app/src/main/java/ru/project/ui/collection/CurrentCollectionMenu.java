package ru.project.ui.collection;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.collection.counting.CountOccurrencesMenu;
import ru.project.ui.collection.search.SearchCollectionMenu;
import ru.project.ui.collection.sorting.SortCollectionMenu;
import ru.project.ui.common.CollectionDisplayWindow;
import ru.project.ui.common.CollectionFileSaveWindow;

public class CurrentCollectionMenu extends BaseModalWindow {

  private final AppState state;

  private final SortCollectionMenu sortCollectionMenu;
  private final SearchCollectionMenu searchCollectionMenu;
  private final CountOccurrencesMenu countOccurrencesMenu;

  public CurrentCollectionMenu(WindowBasedTextGUI gui, AppState state) {

    super("Меню текущей коллекции", gui);

    this.state = state;

    this.sortCollectionMenu = new SortCollectionMenu(gui, state);

    this.searchCollectionMenu = new SearchCollectionMenu(gui, state);

    this.countOccurrencesMenu = new CountOccurrencesMenu(gui, state);

    ActionListBox menu = new ActionListBox();
    menu.addItem("Показать коллекцию...", this::showCurrentCollection);

    menu.addItem("Сохранить коллекцию в файл...", this::saveCurrentCollection);

    menu.addItem("Меню сортировки коллекции", sortCollectionMenu::showModal);

    menu.addItem("Меню поиска элемента в коллекции", searchCollectionMenu::showModal);

    menu.addItem("Меню подсчёта количества вхождений элемента", countOccurrencesMenu::showModal);

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
}
