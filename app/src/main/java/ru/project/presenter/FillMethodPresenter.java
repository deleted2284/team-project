package ru.project.presenter;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Consumer;
import ru.project.model.AppState;
import ru.project.model.FillMethod;
import ru.project.view.FillMethodView;

public class FillMethodPresenter extends BasePresenter {

  private final FillMethodView view;
  private final Consumer<FillMethod> onMethodSelected;

  public FillMethodPresenter(
      AppState state,
      FillMethodView view,
      WindowBasedTextGUI gui,
      Consumer<FillMethod> onMethodSelected) {

    super(state, gui);

    this.view = view;
    this.onMethodSelected = onMethodSelected;
  }

  @Override
  public void start() {
    bindActions();
    view.show(gui);
  }

  private void bindActions() {
    view.setCustomAction(() -> selectMethod(FillMethod.CUSTOM));

    view.setRandomAction(() -> selectMethod(FillMethod.RANDOM));

    view.setFileAction(() -> selectMethod(FillMethod.FILE));
  }

  private void selectMethod(FillMethod method) {
    onMethodSelected.accept(method);
    view.close();
  }
}
