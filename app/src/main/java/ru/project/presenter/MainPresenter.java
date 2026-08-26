package ru.project.presenter;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;
import ru.project.view.CollectionView;
import ru.project.view.CreateCollectionView;
import ru.project.view.MainView;

public class MainPresenter extends BasePresenter {

  private final MainView view;

  private final CollectionPresenter collectionPresenter;
  private final CreateCollectionPresenter createCollectionPresenter;

  public MainPresenter(AppState state, MainView view, WindowBasedTextGUI gui) {
    super(state, gui);

    this.view = view;

    this.collectionPresenter = new CollectionPresenter(state, new CollectionView(), gui);

    this.createCollectionPresenter =
        new CreateCollectionPresenter(state, new CreateCollectionView(), gui);

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
    collectionPresenter.start();
  }

  private void createCollection() {
    createCollectionPresenter.start();
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
