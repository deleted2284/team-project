package ru.project.ui;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

public class MainMenu extends BasicWindow {

  public MainMenu(Navigator navigator) {
    super("ГЛАВНОЕ МЕНЮ");

    Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

    panel.addComponent(new Button("Показать текущую коллекцию", navigator::showCollectionView));

    panel.addComponent(new Button("Создать новую коллекцию", navigator::showCreateCollection));

    panel.addComponent(
        new Button("Сохранить текущую коллекцию в файл", navigator::showSaveCollection));

    panel.addComponent(new Button("Сортировать текущую коллекцию", navigator::showSortCollection));

    panel.addComponent(new Button("Найти элемент", navigator::showSearchElement));

    panel.addComponent(
        new Button("Подсчитать количество вхождений", navigator::showCountOccurrences));

    panel.addComponent(new Button("Завершить выполнение программы", this::close));

    setComponent(panel);
  }
}
