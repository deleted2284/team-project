package ru.project.presenter;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;
import ru.project.model.FillMethod;
import ru.project.view.CreateCollectionView;
import ru.project.view.FillMethodView;

public class CreateCollectionPresenter extends BasePresenter {

  private final CreateCollectionView view;

  private int size;
  private FillMethod fillMethod;

  public CreateCollectionPresenter(
      AppState state, CreateCollectionView view, WindowBasedTextGUI gui) {

    super(state, gui);

    this.view = view;
    this.size = 0;
    this.fillMethod = FillMethod.CUSTOM;
  }

  @Override
  public void start() {
    bindActions();
    view.show(gui);
  }

  private void bindActions() {
    view.setSetSizeAction(this::setSize);
    view.setConfigureFillMethodAction(this::configureFillMethod);
    view.setCreateCollectionAction(this::createCollection);
  }

  private void setSize() {
    view.showSetSizeDialog(gui, this::handleSizeEntered);
  }

  private void handleSizeEntered(int sizeValue) {
    if (sizeValue <= 0) {
      view.showMessage(gui, "Размер должен быть больше 0.");
      return;
    }

    size = sizeValue;
    view.updateSize(size);
  }

  private void configureFillMethod() {
    FillMethodView fillMethodView = new FillMethodView();

    FillMethodPresenter presenter =
        new FillMethodPresenter(state, fillMethodView, gui, this::handleFillMethodSelected);

    presenter.start();
  }

  private void handleFillMethodSelected(FillMethod selectedMethod) {
    fillMethod = selectedMethod;

    view.updateFillMethod(fillMethod);
  }

  private void createCollection() {
    if (size <= 0) {
      view.showMessage(gui, "Сначала задайте размер коллекции.");
      return;
    }

    switch (fillMethod) {
      case CUSTOM:
        view.showMessage(gui, "Выбран способ заполнения: пользовательские данные.");
        break;

      case RANDOM:
        view.showMessage(gui, "Выбран способ заполнения: случайные данные.");
        break;

      case FILE:
        view.showMessage(gui, "Выбран способ заполнения: данные из файла.");
        break;
    }

    // TODO:
    // state.setCollection(...);
    // view.close();
  }
}
