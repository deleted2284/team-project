package ru.project.ui.collection.counting;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.collection.MyList;
import ru.project.finder.StudentOccurrenceIndexFinder;
import ru.project.model.AppState;
import ru.project.student.Student;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.common.MessageWindow;

public class CountOccurrencesMenu extends BaseModalWindow {

  private final AppState state;

  private final OccurrenceStudentSettingsWindow occurrenceStudentSettingsWindow;

  private Student selectedStudent;
  private int occurrences;

  private final Label studentLabel;
  private final Label occurrencesLabel;

  public CountOccurrencesMenu(WindowBasedTextGUI gui, AppState state) {

    super("Меню подсчёта количества вхождений элемента", gui);

    this.state = state;
    this.selectedStudent = null;
    this.occurrences = 0;

    this.studentLabel = new Label("Элемент для поиска: не задан");

    this.occurrencesLabel = new Label("Количество вхождений: не подсчитано");

    this.occurrenceStudentSettingsWindow =
        new OccurrenceStudentSettingsWindow(
            gui,
            student -> {
              selectedStudent = student;

              studentLabel.setText("Элемент для поиска: задан");
            });

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    panel.addComponent(studentLabel);
    panel.addComponent(occurrencesLabel);

    panel.addComponent(new Button("Настроить элемент для поиска...", this::setupStudent));

    panel.addComponent(new Button("Выполнить подсчёт", this::countOccurrences));

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void setupStudent() {
    occurrenceStudentSettingsWindow.showModal();
  }

  private void countOccurrences() {
    MyList<Student> collection = state.getMainCollection();

    if (collection == null || collection.isEmpty()) {
      MessageWindow.showModal(gui, "Текущая коллекция пуста.");

      return;
    }

    if (selectedStudent == null) {
      MessageWindow.showModal(gui, "Сначала настройте элемент для поиска.");

      return;
    }

    StudentOccurrenceIndexFinder finder = new StudentOccurrenceIndexFinder();

    int[] occurrenceIndices = finder.findOccurrences(collection, selectedStudent);

    occurrences = occurrenceIndices.length;

    StringBuilder result = new StringBuilder();

    result.append("Количество вхождений: ").append(occurrences);

    result.append("\nИндексы: ");

    if (occurrenceIndices.length == 0) {
      result.append("нет");
    } else {
      for (int i = 0; i < occurrenceIndices.length; i++) {

        if (i > 0) {
          result.append(", ");
        }

        result.append(occurrenceIndices[i]);
      }
    }

    occurrencesLabel.setText(result.toString());
  }
}
