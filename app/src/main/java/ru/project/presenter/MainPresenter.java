package ru.project.presenter;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;
import ru.project.view.MainView;

public class MainPresenter extends BasePresenter {

  private final MainView view;

  public MainPresenter(AppState state, MainView view, WindowBasedTextGUI gui) {
    super(state, gui);
    this.view = view;

    bindActions();
  }

  @Override
  public void start() {
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
    // TODO
  }

  private void createCollection() {
    // TODO
  }

  private void saveCollection() {
    // TODO
  }

  private void sortCollection() {
    // TODO
  }

  private void searchCollection() {
    // TODO
  }

  private void countOccurrences() {
    // TODO
  }

  private void exit() {
    view.close();
  }
}
