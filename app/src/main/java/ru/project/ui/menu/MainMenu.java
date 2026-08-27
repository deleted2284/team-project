package ru.project.ui.menu;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;
import ru.project.ui.base.BaseWindow;
import ru.project.ui.window.CurrentCollectionWindow;
import ru.project.ui.window.MessageWindow;

public class MainMenu extends BaseWindow {

  private final ActionListBox menu;
  private final AppState state;

  public MainMenu(WindowBasedTextGUI gui, AppState state) {
    super("Главное меню", gui);

    this.state = state;

    menu = new ActionListBox();

    menu.addItem("Показать текущую коллекцию", this::showCurrentCollection);

    menu.addItem("Меню создания новой коллекции", this::createCollection);

    menu.addItem("Меню сохранения текущей коллекции в файл", this::saveCollection);

    menu.addItem("Меню сортировки текущей коллекции", this::sortCollection);

    menu.addItem("Меню поиска элемента в текущей коллекции", this::searchCollection);

    menu.addItem("Меню подсчёта количества вхождений элемента", this::countOccurrences);

    menu.addItem("Завершить выполнение программы", this::exit);

    setComponent(menu);
  }

  private void showCurrentCollection() {
    CurrentCollectionWindow window = new CurrentCollectionWindow(gui, state);

    window.show();
  }

  private void createCollection() {
    CreateCollectionMenu window = new CreateCollectionMenu(gui, state);

    window.show();
  }

  private void saveCollection() {
    // TODO
    MessageWindow.create(gui, "Не реализовано.");
  }

  private void sortCollection() {
    // TODO
    MessageWindow.create(gui, "Не реализовано.");
  }

  private void searchCollection() {
    // TODO
    MessageWindow.create(gui, "Не реализовано.");
  }

  private void countOccurrences() {
    // TODO
    MessageWindow.create(gui, "Не реализовано.");
  }

  private void exit() {
    close();
  }
}
