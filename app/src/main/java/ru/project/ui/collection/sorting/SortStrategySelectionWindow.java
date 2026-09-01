package ru.project.ui.collection.sorting;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Consumer;
import ru.project.ui.base.BaseModalWindow;

public class SortStrategySelectionWindow extends BaseModalWindow {

  private final Consumer<SortAlgorithm> onSortAlgorithmSelected;

  public SortStrategySelectionWindow(
      WindowBasedTextGUI gui, Consumer<SortAlgorithm> onSortAlgorithmSelected) {

    super("Выбор алгоритма сортировки", gui);

    this.onSortAlgorithmSelected = onSortAlgorithmSelected;

    ActionListBox menu = new ActionListBox();

    menu.addItem("Сортировка пузырьком", () -> selectSortAlgorithm(SortAlgorithm.BUBBLE));

    menu.addItem("Сортировка слиянием", () -> selectSortAlgorithm(SortAlgorithm.MERGE));

    menu.addItem("Выйти", this::close);

    setComponent(menu);
  }

  private void selectSortAlgorithm(SortAlgorithm sortAlgorithm) {
    onSortAlgorithmSelected.accept(sortAlgorithm);
    close();
  }
}
