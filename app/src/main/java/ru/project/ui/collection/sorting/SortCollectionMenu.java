package ru.project.ui.collection.sorting;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.Comparator;
import ru.project.model.AppState;
import ru.project.sorting.BubbleSortStrategy;
import ru.project.sorting.EvenOnlySortStrategy;
import ru.project.sorting.MergeSortStrategy;
import ru.project.sorting.SortStrategy;
import ru.project.sorting.StudentAverageGradeComparator;
import ru.project.sorting.StudentGroupComparator;
import ru.project.sorting.StudentRecordBookComparator;
import ru.project.student.Student;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.common.MessageWindow;

public class SortCollectionMenu extends BaseModalWindow {

  private final AppState state;

  private final Label sortAlgorithmLabel;
  private final Label sortFieldLabel;
  private final Label sortMethodLabel;

  private final NormalSortSettingsWindow normalSortSettingsWindow;
  private final ParitySortSettingsWindow paritySortSettingsWindow;
  private final SortMethodSelectionWindow sortMethodSelectionWindow;
  private final SortStrategySelectionWindow sortStrategySelectionWindow;

  private SortAlgorithm selectedSortAlgorithm;
  private SortField selectedSortField;
  private SortMethod selectedSortMethod;

  public SortCollectionMenu(WindowBasedTextGUI gui, AppState state) {

    super("Меню сортировки текущей коллекции", gui);

    this.state = state;

    this.selectedSortAlgorithm = SortAlgorithm.BUBBLE;
    this.selectedSortField = SortField.GROUP_NUMBER;
    this.selectedSortMethod = SortMethod.NORMAL;

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    this.sortMethodLabel = new Label("Способ сортировки: " + getSortMethodName(selectedSortMethod));

    this.sortAlgorithmLabel =
        new Label("Алгоритм сортировки: " + getSortAlgorithmName(selectedSortAlgorithm));

    this.sortFieldLabel =
        new Label("Сортировать по: " + getSortFieldName(selectedSortMethod, selectedSortField));

    this.sortMethodSelectionWindow =
        new SortMethodSelectionWindow(
            gui,
            sortMethod -> {
              selectedSortMethod = sortMethod;

              sortMethodLabel.setText(
                  "Способ сортировки: " + getSortMethodName(selectedSortMethod));

              sortFieldLabel.setText(
                  "Сортировать по: " + getSortFieldName(selectedSortMethod, selectedSortField));
            });

    this.sortStrategySelectionWindow =
        new SortStrategySelectionWindow(
            gui,
            sortAlgorithm -> {
              selectedSortAlgorithm = sortAlgorithm;

              sortAlgorithmLabel.setText(
                  "Алгоритм сортировки: " + getSortAlgorithmName(selectedSortAlgorithm));
            });

    this.normalSortSettingsWindow =
        new NormalSortSettingsWindow(
            gui,
            sortField -> {
              selectedSortField = sortField;

              sortFieldLabel.setText(
                  "Сортировать по: " + getSortFieldName(selectedSortMethod, selectedSortField));
            });

    this.paritySortSettingsWindow = new ParitySortSettingsWindow(gui);

    panel.addComponent(sortMethodLabel);
    panel.addComponent(sortFieldLabel);
    panel.addComponent(sortAlgorithmLabel);

    panel.addComponent(new Button("Выбрать способ сортировки...", this::selectSortMethod));

    panel.addComponent(new Button("Выбрать алгоритм сортировки...", this::selectSortAlgorithm));

    panel.addComponent(
        new Button("Настроить текущий способ сортировки...", this::setupCurrentSortMethod));

    panel.addComponent(new Button("Сортировать текущую коллекцию", this::sortCurrentCollection));

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void setupCurrentSortMethod() {
    switch (selectedSortMethod) {
      case NORMAL -> normalSortSettingsWindow.showModal();

      case PARITY -> paritySortSettingsWindow.showModal();
    }
  }

  private void selectSortMethod() {
    sortMethodSelectionWindow.showModal();
  }

  private void selectSortAlgorithm() {
    sortStrategySelectionWindow.showModal();
  }

  private void sortCurrentCollection() {
    if (state.getMainCollection() == null || state.getMainCollection().isEmpty()) {

      MessageWindow.showModal(gui, "Текущая коллекция пуста.");

      return;
    }

    switch (selectedSortMethod) {
      case NORMAL -> sortNormally();

      case PARITY -> sortByParity();
    }
  }

  private void sortNormally() {
    SortStrategy<Student> sortStrategy = getSortStrategy();

    Comparator<Student> comparator = getSortComparator();

    sortStrategy.sort(state.getMainCollection(), comparator);

    MessageWindow.showModal(gui, "Обычная сортировка успешно выполнена.");
  }

  private void sortByParity() {
    SortStrategy<Student> sortStrategy =
        new EvenOnlySortStrategy<>(getSortStrategy(), Student::getRecordBookNumber);

    Comparator<Student> comparator = new StudentRecordBookComparator();

    sortStrategy.sort(state.getMainCollection(), comparator);

    MessageWindow.showModal(gui, "Сортировка по чётности успешно выполнена.");
  }

  private SortStrategy<Student> getSortStrategy() {
    return switch (selectedSortAlgorithm) {
      case BUBBLE -> new BubbleSortStrategy<>();

      case MERGE -> new MergeSortStrategy<>();
    };
  }

  private Comparator<Student> getSortComparator() {
    return switch (selectedSortField) {
      case GROUP_NUMBER -> new StudentGroupComparator();

      case AVERAGE_GRADE -> new StudentAverageGradeComparator();

      case RECORD_BOOK_NUMBER -> new StudentRecordBookComparator();
    };
  }

  private String getSortMethodName(SortMethod sortMethod) {
    return switch (sortMethod) {
      case NORMAL -> "Обычная сортировка";

      case PARITY -> "Сортировка по чётности";
    };
  }

  private String getSortAlgorithmName(SortAlgorithm sortAlgorithm) {
    return switch (sortAlgorithm) {
      case BUBBLE -> "Сортировка пузырьком";

      case MERGE -> "Сортировка слиянием";
    };
  }

  private String getSortFieldName(SortMethod sortMethod, SortField sortField) {
    return switch (sortMethod) {
      case NORMAL -> getNormalSortFieldName(sortField);

      case PARITY -> "номеру зачётной книжки";
    };
  }

  private String getNormalSortFieldName(SortField sortField) {
    return switch (sortField) {
      case GROUP_NUMBER -> "номеру группы";

      case AVERAGE_GRADE -> "среднему баллу";

      case RECORD_BOOK_NUMBER -> "номеру зачётной книжки";
    };
  }
}
