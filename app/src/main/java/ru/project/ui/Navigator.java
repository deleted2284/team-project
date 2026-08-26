package ru.project.ui;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.CollectionController;

public class Navigator {

  private final WindowBasedTextGUI gui;
  private final CollectionController collectionController;

  public Navigator(WindowBasedTextGUI gui, CollectionController collectionController) {
    this.gui = gui;
    this.collectionController = collectionController;
  }

  public void show(BasicWindow window) {
    gui.addWindowAndWait(window);
  }

  public void showCollectionView() {
    show(new CollectionView(collectionController));
  }

  public void showCreateCollection() {
    show(new CreateCollectionMenu(collectionController));
  }

  public void showSaveCollection() {
    show(new SaveCollectionMenu(collectionController));
  }

  public void showSortCollection() {
    show(new SortCollectionMenu(collectionController));
  }

  public void showSearchElement() {
    show(new SearchElementMenu(collectionController));
  }

  public void showCountOccurrences() {
    show(new CountOccurrencesMenu(collectionController));
  }
}
