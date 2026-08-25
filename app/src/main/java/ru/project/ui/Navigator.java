package ru.project.ui;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

public class Navigator {

  private final WindowBasedTextGUI gui;

  public Navigator(WindowBasedTextGUI gui) {
    this.gui = gui;
  }

  public void show(BasicWindow window) {
    gui.addWindowAndWait(window);
  }

  public void showCollectionView() {
    show(new CollectionView());
  }

  public void showCreateCollection() {
    show(new CreateCollectionMenu());
  }

  public void showSaveCollection() {
    show(new SaveCollectionMenu());
  }

  public void showSortCollection() {
    show(new SortCollectionMenu());
  }

  public void showSearchElement() {
    show(new SearchElementMenu());
  }

  public void showCountOccurrences() {
    show(new CountOccurrencesMenu());
  }
}
