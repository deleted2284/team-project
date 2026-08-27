package ru.project.ui.menu;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Consumer;
import ru.project.model.FillMethod;
import ru.project.ui.base.BaseWindow;

public class FillMethodSelectionWindow extends BaseWindow {

  private final Consumer<FillMethod> onFillMethodSelected;

  public FillMethodSelectionWindow(
      WindowBasedTextGUI gui, Consumer<FillMethod> onFillMethodSelected) {

    super("Выбор способа заполнения", gui);

    this.onFillMethodSelected = onFillMethodSelected;

    ActionListBox menu = new ActionListBox();

    menu.addItem("Пользовательские данные", () -> selectFillMethod(FillMethod.CUSTOM));

    menu.addItem("Случайные данные", () -> selectFillMethod(FillMethod.RANDOM));

    menu.addItem("Данные из файла", () -> selectFillMethod(FillMethod.FILE));

    menu.addItem("Выйти", this::close);

    setComponent(menu);
  }

  private void selectFillMethod(FillMethod fillMethod) {
    onFillMethodSelected.accept(fillMethod);
    close();
  }
}
