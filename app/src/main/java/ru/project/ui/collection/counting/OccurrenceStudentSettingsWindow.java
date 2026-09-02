package ru.project.ui.collection.counting;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Consumer;
import ru.project.student.Student;
import ru.project.student.StudentBuilder;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.common.InputValueWindow;
import ru.project.ui.common.MessageWindow;

public class OccurrenceStudentSettingsWindow extends BaseModalWindow {

  private final StudentBuilder studentBuilder;
  private final Consumer<Student> onApply;

  private String groupNumber;
  private Double averageGrade;
  private Integer recordBookNumber;

  private final Label groupNumberLabel;
  private final Label averageGradeLabel;
  private final Label recordBookNumberLabel;

  public OccurrenceStudentSettingsWindow(WindowBasedTextGUI gui, Consumer<Student> onApply) {

    super("Настройка элемента для поиска", gui);

    this.studentBuilder = new StudentBuilder();
    this.onApply = onApply;

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    groupNumberLabel = new Label("Номер группы: не задан");

    averageGradeLabel = new Label("Средний балл: не задан");

    recordBookNumberLabel = new Label("Номер зачётной книжки: не задан");

    panel.addComponent(groupNumberLabel);
    panel.addComponent(averageGradeLabel);
    panel.addComponent(recordBookNumberLabel);

    panel.addComponent(new Button("Задать номер группы...", this::setGroupNumber));

    panel.addComponent(new Button("Задать средний балл...", this::setAverageGrade));

    panel.addComponent(new Button("Задать номер зачётной книжки...", this::setRecordBookNumber));

    panel.addComponent(new Button("Применить", this::apply));

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void setGroupNumber() {
    InputValueWindow window =
        new InputValueWindow(
            gui,
            "Номер группы",
            "Введите номер группы (например, A12):",
            groupNumber,
            value -> {
              if (!value.matches(Student.getGroupNumberPattern())) {

                MessageWindow.showModal(gui, "Номер группы должен соответствовать формату A12.");

                return false;
              }

              groupNumber = value;
              studentBuilder.setGroupNumber(value);

              groupNumberLabel.setText("Номер группы: " + groupNumber);

              return true;
            });

    window.showModal();
  }

  private void setAverageGrade() {
    InputValueWindow window =
        new InputValueWindow(
            gui,
            "Средний балл",
            "Введите средний балл от 0.0 до 5.0:",
            averageGrade == null ? null : String.valueOf(averageGrade),
            value -> {
              try {
                double parsedValue = Double.parseDouble(value);

                if (parsedValue < Student.getMinAverageGrade()
                    || parsedValue > Student.getMaxAverageGrade()) {

                  MessageWindow.showModal(gui, "Средний балл должен быть от 0.0 до 5.0.");

                  return false;
                }

                averageGrade = parsedValue;
                studentBuilder.setAverageGrade(parsedValue);

                averageGradeLabel.setText("Средний балл: " + averageGrade);

                return true;

              } catch (NumberFormatException e) {
                MessageWindow.showModal(gui, "Введите корректное число.");

                return false;
              }
            });

    window.showModal();
  }

  private void setRecordBookNumber() {
    InputValueWindow window =
        new InputValueWindow(
            gui,
            "Номер зачётной книжки",
            "Введите номер зачётной книжки:",
            recordBookNumber == null ? null : String.valueOf(recordBookNumber),
            value -> {
              try {
                int parsedValue = Integer.parseInt(value);

                if (parsedValue < Student.getMinRecordBookNumber()) {

                  MessageWindow.showModal(gui, "Номер зачётной книжки должен быть положительным.");

                  return false;
                }

                recordBookNumber = parsedValue;
                studentBuilder.setRecordBookNumber(parsedValue);

                recordBookNumberLabel.setText("Номер зачётной книжки: " + recordBookNumber);

                return true;

              } catch (NumberFormatException e) {
                MessageWindow.showModal(gui, "Введите целое число.");

                return false;
              }
            });

    window.showModal();
  }

  private void apply() {
    if (groupNumber == null || averageGrade == null || recordBookNumber == null) {

      MessageWindow.showModal(gui, "Необходимо задать все поля элемента.");

      return;
    }

    Student student = studentBuilder.build();

    onApply.accept(student);

    MessageWindow.showModal(gui, "Элемент для поиска успешно настроен.");
  }
}
