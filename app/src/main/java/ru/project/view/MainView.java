package ru.project.view;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

public class MainView {

  private final BasicWindow window;
  private final ActionListBox menu;

  public MainView() {
    window = new BasicWindow("Главное меню");

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    menu = new ActionListBox();

    panel.addComponent(menu);

    window.setComponent(panel);
  }

  public void setShowCollectionAction(Runnable action) {
    menu.addItem("Показать текущую коллекцию", action);
  }

  public void setCreateCollectionAction(Runnable action) {
    menu.addItem("Меню создания новой коллекции", action);
  }

  public void setSaveCollectionAction(Runnable action) {
    menu.addItem("Меню сохранения текущей коллекции в файл", action);
  }

  public void setSortCollectionAction(Runnable action) {
    menu.addItem("Меню сортировки текущей коллекции", action);
  }

  public void setSearchCollectionAction(Runnable action) {
    menu.addItem("Меню поиска элемента в текущей коллекции", action);
  }

  public void setCountOccurrencesAction(Runnable action) {
    menu.addItem("Меню подсчёта количества вхождений элемента", action);
  }

  public void setExitAction(Runnable action) {
    menu.addItem("Завершить выполнение программы", action);
  }

  public void show(WindowBasedTextGUI gui) {
    gui.addWindowAndWait(window);
  }

  public void close() {
    window.close();
  }
}
