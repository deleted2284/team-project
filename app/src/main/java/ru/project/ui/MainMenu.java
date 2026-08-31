package ru.project.ui;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.collection.CurrentCollectionMenu;
import ru.project.ui.creation.CreateCollectionMenu;

public class MainMenu extends BaseModalWindow {

  private final ActionListBox menu;

  private final BaseModalWindow currentCollectionMenu;
  private final BaseModalWindow createCollectionMenu;

  public MainMenu(WindowBasedTextGUI gui, AppState state) {
    super("Главное меню", gui);

    this.currentCollectionMenu = new CurrentCollectionMenu(gui, state);

    this.createCollectionMenu = new CreateCollectionMenu(gui, state);

    menu = new ActionListBox();

    menu.addItem("Меню текущей коллекции", currentCollectionMenu::showModal);

    menu.addItem("Меню создания новой коллекции", createCollectionMenu::showModal);

    menu.addItem("Завершить выполнение программы", this::exit);

    setComponent(menu);
  }

  private void exit() {
    close();
  }
}
