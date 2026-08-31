package ru.project.ui.creation.random;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Consumer;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.common.InputValueWindow;
import ru.project.ui.common.MessageWindow;

public class RandomDataSettingsWindow extends BaseModalWindow {

  private final RandomFillSettings settings;
  private final Consumer<RandomFillSettings> onApply;

  private final Label minGroupNumberLabel;
  private final Label maxGroupNumberLabel;
  private final Label minAverageGradeLabel;
  private final Label maxAverageGradeLabel;
  private final Label minRecordBookNumberLabel;
  private final Label maxRecordBookNumberLabel;

  public RandomDataSettingsWindow(WindowBasedTextGUI gui, Consumer<RandomFillSettings> onApply) {

    super("Настройка случайного заполнения", gui);

    this.settings = new RandomFillSettings();
    this.onApply = onApply;

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    minGroupNumberLabel =
        new Label("Минимальный номер группы: " + getValueOrDefault(settings.getMinGroupNumber()));

    maxGroupNumberLabel =
        new Label("Максимальный номер группы: " + getValueOrDefault(settings.getMaxGroupNumber()));

    minAverageGradeLabel =
        new Label("Минимальный средний балл: " + getValueOrDefault(settings.getMinAverageGrade()));

    maxAverageGradeLabel =
        new Label("Максимальный средний балл: " + getValueOrDefault(settings.getMaxAverageGrade()));

    minRecordBookNumberLabel =
        new Label(
            "Минимальный номер зачётной книжки: "
                + getValueOrDefault(settings.getMinRecordBookNumber()));

    maxRecordBookNumberLabel =
        new Label(
            "Максимальный номер зачётной книжки: "
                + getValueOrDefault(settings.getMaxRecordBookNumber()));

    panel.addComponent(minGroupNumberLabel);
    panel.addComponent(maxGroupNumberLabel);
    panel.addComponent(minAverageGradeLabel);
    panel.addComponent(maxAverageGradeLabel);
    panel.addComponent(minRecordBookNumberLabel);
    panel.addComponent(maxRecordBookNumberLabel);

    panel.addComponent(new Button("Задать минимальный номер группы", this::setMinGroupNumber));

    panel.addComponent(new Button("Задать максимальный номер группы", this::setMaxGroupNumber));

    panel.addComponent(new Button("Задать минимальный средний балл", this::setMinAverageGrade));

    panel.addComponent(new Button("Задать максимальный средний балл", this::setMaxAverageGrade));

    panel.addComponent(
        new Button("Задать минимальный номер зачётной книжки", this::setMinRecordBookNumber));

    panel.addComponent(
        new Button("Задать максимальный номер зачётной книжки", this::setMaxRecordBookNumber));

    panel.addComponent(new Button("Применить", this::apply));

    panel.addComponent(new Button("Назад", this::close));

    setComponent(panel);
  }

  private void setMinGroupNumber() {
    showGroupNumberInput(
        "Минимальный номер группы",
        settings.getMinGroupNumber(),
        value -> {
          if (!value.matches("[A-Z]\\d{2}")) {

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

          minGroupNumberLabel.setText("Минимальный номер группы: " + value);

          return true;
        });
  }

  private void setMaxGroupNumber() {
    showGroupNumberInput(
        "Максимальный номер группы",
        settings.getMaxGroupNumber(),
        value -> {
          if (!value.matches("[A-Z]\\d{2}")) {

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

          maxGroupNumberLabel.setText("Максимальный номер группы: " + value);

          return true;
        });
  }

  private void showGroupNumberInput(
      String title, String initialValue, java.util.function.Predicate<String> onSave) {

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

          minAverageGradeLabel.setText("Минимальный средний балл: " + value);

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

          maxAverageGradeLabel.setText("Максимальный средний балл: " + value);

          return true;
        });
  }

  private void showAverageGradeInput(
      String title, Double initialValue, java.util.function.Predicate<Double> onSave) {

    InputValueWindow window =
        new InputValueWindow(
            gui,
            title,
            "Введите значение от " + "0.0 до 5.0:",
            initialValue == null ? null : String.valueOf(initialValue),
            value -> {
              try {
                double parsedValue = Double.parseDouble(value);

                if (parsedValue < ru.project.student.Student.getMinAverageGrade()
                    || parsedValue > ru.project.student.Student.getMaxAverageGrade()) {

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

          minRecordBookNumberLabel.setText("Минимальный номер зачётной книжки: " + value);

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

          maxRecordBookNumberLabel.setText("Максимальный номер зачётной книжки: " + value);

          return true;
        });
  }

  private void showRecordBookNumberInput(
      String title, Integer initialValue, java.util.function.Predicate<Integer> onSave) {

    InputValueWindow window =
        new InputValueWindow(
            gui,
            title,
            "Введите номер зачётной книжки:",
            initialValue == null ? null : String.valueOf(initialValue),
            value -> {
              try {
                int parsedValue = Integer.parseInt(value);

                if (parsedValue < ru.project.student.Student.getMinRecordBookNumber()) {

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
    onApply.accept(settings);

    MessageWindow.showModal(gui, "Настройки создания коллекции случайно успешно применены.");
  }

  private String getValueOrDefault(Object value) {
    return value == null ? "не задан" : String.valueOf(value);
  }
}
