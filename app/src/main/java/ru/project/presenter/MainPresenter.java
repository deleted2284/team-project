package ru.project.presenter;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import ru.project.model.AppState;
import ru.project.view.CollectionView;
import ru.project.view.MainView;

public class MainPresenter {

  private final AppState state;
  private final MainView view;
  private final WindowBasedTextGUI gui;
  private final CollectionPresenter collectionPresenter;

  public MainPresenter(AppState state, MainView view, WindowBasedTextGUI gui) {
    this.state = state;
    this.view = view;
    this.gui = gui;
    this.collectionPresenter = new CollectionPresenter(state, new CollectionView(), gui);
  }

  public void start() {
    bindActions();
    view.show(gui);
  }

  private void bindActions() {
    view.setShowCollectionAction(this::showCollection);
    view.setCreateCollectionAction(this::createCollection);
    view.setSaveCollectionAction(this::saveCollection);
    view.setSortCollectionAction(this::sortCollection);
    view.setSearchCollectionAction(this::searchCollection);
    view.setCountOccurrencesAction(this::countOccurrences);
    view.setExitAction(this::exit);
  }

  private void showCollection() {
    collectionPresenter.start();
  }

  private void createCollection() {
    showMessage("Меню создания коллекции.");
  }

  private void saveCollection() {
    showMessage("Меню сохранения коллекции.");
  }

  private void sortCollection() {
    showMessage("Меню сортировки коллекции.");
  }

  private void searchCollection() {
    showMessage("Меню поиска элемента.");
  }

  private void countOccurrences() {
    showMessage("Меню подсчёта количества вхождений.");
  }

  private void exit() {
    view.close();
  }

  private void showMessage(String message) {
    MessageDialog.showMessageDialog(gui, "Информация", message, MessageDialogButton.OK);
  }
}
