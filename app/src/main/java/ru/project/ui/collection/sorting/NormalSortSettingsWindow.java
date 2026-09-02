package ru.project.ui.collection.sorting;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Consumer;
import ru.project.ui.base.BaseModalWindow;

public class NormalSortSettingsWindow extends BaseModalWindow {

  private final Consumer<SortField> onSortFieldSelected;

  public NormalSortSettingsWindow(WindowBasedTextGUI gui, Consumer<SortField> onSortFieldSelected) {

    super("Обычная сортировка", gui);

    this.onSortFieldSelected = onSortFieldSelected;

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    ActionListBox menu = new ActionListBox();

    menu.addItem("Сортировать по номеру группы", () -> selectSortField(SortField.GROUP_NUMBER));

    menu.addItem("Сортировать по среднему баллу", () -> selectSortField(SortField.AVERAGE_GRADE));

    menu.addItem(
        "Сортировать по номеру зачётной книжки",
        () -> selectSortField(SortField.RECORD_BOOK_NUMBER));

    panel.addComponent(menu);

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void selectSortField(SortField sortField) {
    onSortFieldSelected.accept(sortField);
    close();
  }
}
