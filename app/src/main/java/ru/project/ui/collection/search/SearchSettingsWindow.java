package ru.project.ui.collection.search;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.CheckBox;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.RadioBoxList;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Consumer;
import java.util.function.Predicate;
import ru.project.student.Student;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.common.InputValueWindow;
import ru.project.ui.common.MessageWindow;

public class SearchSettingsWindow extends BaseModalWindow {

  private final SearchSettings settings;
  private final Consumer<SearchSettings> onApply;

  private final CheckBox groupNumberFilterCheckBox;
  private final CheckBox averageGradeFilterCheckBox;
  private final CheckBox recordBookNumberFilterCheckBox;

  private final Label minGroupNumberLabel;
  private final Label maxGroupNumberLabel;

  private final Label minAverageGradeLabel;
  private final Label maxAverageGradeLabel;

  private final Label minRecordBookNumberLabel;
  private final Label maxRecordBookNumberLabel;

  private final RadioBoxList<String> relationList;

  public SearchSettingsWindow(WindowBasedTextGUI gui, Consumer<SearchSettings> onApply) {

    super("Настройка процесса поиска", gui);

    this.settings = new SearchSettings();
    this.onApply = onApply;

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    panel.addComponent(new Label("Отношение фильтров:"));

    relationList = new RadioBoxList<>();
    relationList.addItem("И");
    relationList.addItem("ИЛИ");
    relationList.setCheckedItemIndex(0);

    panel.addComponent(relationList);

    groupNumberFilterCheckBox = new CheckBox("Фильтр по номеру группы");

    averageGradeFilterCheckBox = new CheckBox("Фильтр по среднему баллу");

    recordBookNumberFilterCheckBox = new CheckBox("Фильтр по номеру зачётной книжки");

    panel.addComponent(groupNumberFilterCheckBox);

    minGroupNumberLabel =
        new Label("Минимум номера группы: " + getValueOrDefault(settings.getMinGroupNumber()));

    maxGroupNumberLabel =
        new Label("Максимум номера группы: " + getValueOrDefault(settings.getMaxGroupNumber()));

    panel.addComponent(minGroupNumberLabel);
    panel.addComponent(maxGroupNumberLabel);

    panel.addComponent(new Button("Задать минимум номера группы...", this::setMinGroupNumber));

    panel.addComponent(new Button("Задать максимум номера группы...", this::setMaxGroupNumber));

    panel.addComponent(averageGradeFilterCheckBox);

    minAverageGradeLabel =
        new Label("Минимум среднего балла: " + getValueOrDefault(settings.getMinAverageGrade()));

    maxAverageGradeLabel =
        new Label("Максимум среднего балла: " + getValueOrDefault(settings.getMaxAverageGrade()));

    panel.addComponent(minAverageGradeLabel);
    panel.addComponent(maxAverageGradeLabel);

    panel.addComponent(new Button("Задать минимум среднего балла...", this::setMinAverageGrade));

    panel.addComponent(new Button("Задать максимум среднего балла...", this::setMaxAverageGrade));

    panel.addComponent(recordBookNumberFilterCheckBox);

    minRecordBookNumberLabel =
        new Label(
            "Минимум номера зачётной книжки: "
                + getValueOrDefault(settings.getMinRecordBookNumber()));

    maxRecordBookNumberLabel =
        new Label(
            "Максимум номера зачётной книжки: "
                + getValueOrDefault(settings.getMaxRecordBookNumber()));

    panel.addComponent(minRecordBookNumberLabel);
    panel.addComponent(maxRecordBookNumberLabel);

    panel.addComponent(
        new Button("Задать минимум номера зачётной книжки...", this::setMinRecordBookNumber));

    panel.addComponent(
        new Button("Задать максимум номера зачётной книжки...", this::setMaxRecordBookNumber));

    panel.addComponent(new Button("Применить", this::apply));

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void setMinGroupNumber() {
    showGroupNumberInput(
        "Минимальный номер группы",
        settings.getMinGroupNumber(),
        value -> {
          if (!value.matches(Student.getGroupNumberPattern())) {

            MessageWindow.showModal(gui, "Номер группы должен соответствовать формату A12.");

            return false;
          }

          if (settings.getMaxGroupNumber() != null
              && value.compareTo(settings.getMaxGroupNumber()) > 0) {

            MessageWindow.showModal(
                gui, "Минимальный номер группы не может быть больше максимального.");

            return false;
          }

          settings.setMinGroupNumber(value);

          minGroupNumberLabel.setText("Минимум номера группы: " + value);

          return true;
        });
  }

  private void setMaxGroupNumber() {
    showGroupNumberInput(
        "Максимальный номер группы",
        settings.getMaxGroupNumber(),
        value -> {
          if (!value.matches(Student.getGroupNumberPattern())) {

            MessageWindow.showModal(gui, "Номер группы должен соответствовать формату A12.");

            return false;
          }

          if (settings.getMinGroupNumber() != null
              && value.compareTo(settings.getMinGroupNumber()) < 0) {

            MessageWindow.showModal(
                gui, "Максимальный номер группы не может быть меньше минимального.");

            return false;
          }

          settings.setMaxGroupNumber(value);

          maxGroupNumberLabel.setText("Максимум номера группы: " + value);

          return true;
        });
  }

  private void showGroupNumberInput(String title, String initialValue, Predicate<String> onSave) {

    InputValueWindow window =
        new InputValueWindow(
            gui, title, "Введите номер группы (например, A12):", initialValue, onSave);

    window.showModal();
  }

  private void setMinAverageGrade() {
    showAverageGradeInput(
        "Минимальный средний балл",
        settings.getMinAverageGrade(),
        value -> {
          if (settings.getMaxAverageGrade() != null && value > settings.getMaxAverageGrade()) {

            MessageWindow.showModal(
                gui, "Минимальный средний балл не может быть больше максимального.");

            return false;
          }

          settings.setMinAverageGrade(value);

          minAverageGradeLabel.setText("Минимум среднего балла: " + value);

          return true;
        });
  }

  private void setMaxAverageGrade() {
    showAverageGradeInput(
        "Максимальный средний балл",
        settings.getMaxAverageGrade(),
        value -> {
          if (settings.getMinAverageGrade() != null && value < settings.getMinAverageGrade()) {

            MessageWindow.showModal(
                gui, "Максимальный средний балл не может быть меньше минимального.");

            return false;
          }

          settings.setMaxAverageGrade(value);

          maxAverageGradeLabel.setText("Максимум среднего балла: " + value);

          return true;
        });
  }

  private void showAverageGradeInput(String title, Double initialValue, Predicate<Double> onSave) {

    InputValueWindow window =
        new InputValueWindow(
            gui,
            title,
            "Введите значение от 0.0 до 5.0:",
            initialValue == null ? null : String.valueOf(initialValue),
            value -> {
              try {
                double parsedValue = Double.parseDouble(value);

                if (parsedValue < Student.getMinAverageGrade()
                    || parsedValue > Student.getMaxAverageGrade()) {

                  MessageWindow.showModal(gui, "Средний балл должен быть от 0.0 до 5.0.");

                  return false;
                }

                return onSave.test(parsedValue);

              } catch (NumberFormatException e) {
                MessageWindow.showModal(gui, "Введите корректное число.");

                return false;
              }
            });

    window.showModal();
  }

  private void setMinRecordBookNumber() {
    showRecordBookNumberInput(
        "Минимальный номер зачётной книжки",
        settings.getMinRecordBookNumber(),
        value -> {
          if (settings.getMaxRecordBookNumber() != null
              && value > settings.getMaxRecordBookNumber()) {

            MessageWindow.showModal(
                gui, "Минимальный номер зачётной книжки не может быть больше максимального.");

            return false;
          }

          settings.setMinRecordBookNumber(value);

          minRecordBookNumberLabel.setText("Минимум номера зачётной книжки: " + value);

          return true;
        });
  }

  private void setMaxRecordBookNumber() {
    showRecordBookNumberInput(
        "Максимальный номер зачётной книжки",
        settings.getMaxRecordBookNumber(),
        value -> {
          if (settings.getMinRecordBookNumber() != null
              && value < settings.getMinRecordBookNumber()) {

            MessageWindow.showModal(
                gui, "Максимальный номер зачётной книжки не может быть меньше минимального.");

            return false;
          }

          settings.setMaxRecordBookNumber(value);

          maxRecordBookNumberLabel.setText("Максимум номера зачётной книжки: " + value);

          return true;
        });
  }

  private void showRecordBookNumberInput(
      String title, Integer initialValue, Predicate<Integer> onSave) {

    InputValueWindow window =
        new InputValueWindow(
            gui,
            title,
            "Введите номер зачётной книжки:",
            initialValue == null ? null : String.valueOf(initialValue),
            value -> {
              try {
                int parsedValue = Integer.parseInt(value);

                if (parsedValue < Student.getMinRecordBookNumber()) {

                  MessageWindow.showModal(gui, "Номер зачётной книжки должен быть положительным.");

                  return false;
                }

                return onSave.test(parsedValue);

              } catch (NumberFormatException e) {
                MessageWindow.showModal(gui, "Введите целое число.");

                return false;
              }
            });

    window.showModal();
  }

  private void apply() {
    settings.setGroupNumberFilterEnabled(groupNumberFilterCheckBox.isChecked());

    settings.setAverageGradeFilterEnabled(averageGradeFilterCheckBox.isChecked());

    settings.setRecordBookNumberFilterEnabled(recordBookNumberFilterCheckBox.isChecked());

    settings.setRelation(
        relationList.getCheckedItemIndex() == 0 ? FilterRelation.AND : FilterRelation.OR);

    onApply.accept(settings);

    MessageWindow.showModal(gui, "Настройки процесса поиска успешно применены.");
  }

  private String getValueOrDefault(Object value) {
    return value == null ? "не задан" : String.valueOf(value);
  }
}
