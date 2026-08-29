package ru.project.ui.menu;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.collection.MyList;
import ru.project.model.AppState;
import ru.project.student.Student;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.window.CollectionDisplayWindow;
import ru.project.ui.window.MessageWindow;

public class MainMenu extends BaseModalWindow {

  private final ActionListBox menu;

  private final BaseModalWindow createCollectionMenu;
  private final BaseModalWindow sortCollectionMenu;

  public MainMenu(WindowBasedTextGUI gui, AppState state) {
    super("Главное меню", gui);

    this.createCollectionMenu = new CreateCollectionMenu(gui, state);
    this.sortCollectionMenu = new SortCollectionMenu(gui, state);

    menu = new ActionListBox();

    menu.addItem("Показать текущую коллекцию", () -> showMainCollection(state));

    menu.addItem("Меню создания новой коллекции", this::showCreateCollectionMenu);

    menu.addItem("Меню сохранения текущей коллекции в файл", this::saveCollection);

    menu.addItem("Меню сортировки текущей коллекции", this::sortCollection);

    menu.addItem("Меню поиска элемента в текущей коллекции", this::searchCollection);

    menu.addItem("Меню подсчёта количества вхождений элемента", this::countOccurrences);

    menu.addItem("Завершить выполнение программы", this::exit);

    setComponent(menu);
  }

  private void showMainCollection(AppState state) {
    MyList<Student> mainCollection = state.getMainCollection();

    BaseModalWindow window = new CollectionDisplayWindow(gui, mainCollection);

    window.showModal();
  }

  private void showCreateCollectionMenu() {
    createCollectionMenu.showModal();
  }

  private void saveCollection() {
    // TODO
    MessageWindow.showModal(gui, "Не реализовано.");
  }

  private void sortCollection() {
    sortCollectionMenu.showModal();
  }

  private void searchCollection() {
    // TODO
    MessageWindow.showModal(gui, "Не реализовано.");
  }

  private void countOccurrences() {
    // TODO
    MessageWindow.showModal(gui, "Не реализовано.");
  }

  private void exit() {
    close();
  }
}
