package ru.project.ui.window;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Consumer;
import ru.project.model.SortMethod;
import ru.project.ui.base.BaseModalWindow;

public class SortMethodSelectionWindow extends BaseModalWindow {

  private final Consumer<SortMethod> onSortMethodSelected;

  public SortMethodSelectionWindow(
      WindowBasedTextGUI gui, Consumer<SortMethod> onSortMethodSelected) {

    super("Выбрать способ сортировки", gui);

    this.onSortMethodSelected = onSortMethodSelected;

    ActionListBox menu = new ActionListBox();

    menu.addItem("Обычная сортировка", () -> selectSortMethod(SortMethod.NORMAL));

    menu.addItem("Сортировка по чётности", () -> selectSortMethod(SortMethod.PARITY));

    menu.addItem("Выйти", this::close);

    setComponent(menu);
  }

  private void selectSortMethod(SortMethod sortMethod) {
    onSortMethodSelected.accept(sortMethod);
    close();
  }
}
