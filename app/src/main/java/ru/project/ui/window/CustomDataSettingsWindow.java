package ru.project.ui.window;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.model.AppState;
import ru.project.student.Student;
import ru.project.student.StudentBuilder;
import ru.project.ui.base.BaseWindow;

public class CustomDataSettingsWindow extends BaseWindow {

  private final AppState state;
  private final int collectionSize;
  private final StudentBuilder studentBuilder;

  private String groupNumber;
  private Double averageGrade;
  private Integer recordBookNumber;
  private Integer selectedObjectIndex;

  private final Label groupNumberLabel;
  private final Label averageGradeLabel;
  private final Label recordBookNumberLabel;
  private final Label indexLabel;

  private final MyList<Student> preparedCustomDataCollection;

  public CustomDataSettingsWindow(WindowBasedTextGUI gui, AppState state, int collectionSize) {

    super("Настройка пользовательских данных", gui);

    this.state = state;
    this.collectionSize = collectionSize;
    this.studentBuilder = new StudentBuilder();
    this.preparedCustomDataCollection = new MyLinkedList<>();

    MyList<Student> customDataCollection = state.getCustomDataCollection();

    int elementsToCopy = Math.min(customDataCollection.size(), collectionSize);

    for (int i = 0; i < elementsToCopy; i++) {
      preparedCustomDataCollection.add(customDataCollection.get(i));
    }

    for (int i = elementsToCopy; i < collectionSize; i++) {
      preparedCustomDataCollection.add(null);
    }

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    groupNumberLabel = new Label("Номер группы: не задан");

    averageGradeLabel = new Label("Средний балл: не задан");

    recordBookNumberLabel = new Label("Номер зачётной книжки: не задан");

    indexLabel = new Label("Индекс объекта: не задан");

    panel.addComponent(groupNumberLabel);
    panel.addComponent(averageGradeLabel);
    panel.addComponent(recordBookNumberLabel);
    panel.addComponent(indexLabel);

    panel.addComponent(new Button("Задать номер группы", this::setGroupNumber));

    panel.addComponent(new Button("Задать средний балл", this::setAverageGrade));

    panel.addComponent(new Button("Задать номер зачётной книжки", this::setRecordBookNumber));

    panel.addComponent(new Button("Задать индекс объекта", this::setObjectIndex));

    panel.addComponent(new Button("Задать данные объекта", this::editObject));

    panel.addComponent(new Button("Предпросмотр", this::preview));

    panel.addComponent(new Button("Применить", this::apply));

    panel.addComponent(new Button("Назад", this::close));

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
                MessageWindow.show(gui, "Номер группы должен соответствовать формату A12.");
                return false;
              }

              groupNumber = value;
              studentBuilder.setGroupNumber(value);

              groupNumberLabel.setText("Номер группы: " + groupNumber);

              return true;
            });

    window.show();
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

                  MessageWindow.show(gui, "Средний балл должен быть от 0.0 до 5.0.");
                  return false;
                }

                averageGrade = parsedValue;
                studentBuilder.setAverageGrade(parsedValue);

                averageGradeLabel.setText("Средний балл: " + averageGrade);

              } catch (NumberFormatException e) {
                MessageWindow.show(gui, "Введите корректное число.");
                return false;
              }

              return true;
            });

    window.show();
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
                  MessageWindow.show(gui, "Номер зачётной книжки должен быть положительным.");
                  return false;
                }

                recordBookNumber = parsedValue;
                studentBuilder.setRecordBookNumber(parsedValue);

                recordBookNumberLabel.setText("Номер зачётной книжки: " + recordBookNumber);

              } catch (NumberFormatException e) {
                MessageWindow.show(gui, "Введите целое число.");
                return false;
              }

              return true;
            });

    window.show();
  }

  private void setObjectIndex() {
    InputValueWindow window =
        new InputValueWindow(
            gui,
            "Индекс объекта",
            "Введите индекс объекта:",
            selectedObjectIndex == null ? null : String.valueOf(selectedObjectIndex),
            value -> {
              try {
                int index = Integer.parseInt(value);

                if (index < 0) {
                  MessageWindow.show(gui, "Индекс объекта не может быть отрицательным.");
                  return false;
                }

                if (index >= collectionSize) {
                  MessageWindow.show(gui, "Индекс должен быть меньше размера коллекции.");
                  return false;
                }

                selectedObjectIndex = index;

                indexLabel.setText("Индекс объекта: " + selectedObjectIndex);

                return true;

              } catch (NumberFormatException e) {
                MessageWindow.show(gui, "Введите целое число.");
                return false;
              }
            });

    window.show();
  }

  private void editObject() {
    if (!hasStudentData()) {
      MessageWindow.show(gui, "Сначала задайте все поля студента.");
      return;
    }

    if (selectedObjectIndex == null) {
      MessageWindow.show(gui, "Сначала задайте индекс объекта.");
      return;
    }

    if (selectedObjectIndex >= preparedCustomDataCollection.size()) {
      MessageWindow.show(gui, "Индекс объекта выходит за границы подготовленной коллекции.");
      return;
    }

    Student student = studentBuilder.build();

    preparedCustomDataCollection.set(selectedObjectIndex, student);

    MessageWindow.show(gui, "Данные объекта успешно заданы.");
  }

  private void apply() {
    MyList<Student> currentCollection = new MyLinkedList<>();

    for (int i = 0; i < preparedCustomDataCollection.size(); i++) {
      currentCollection.add(preparedCustomDataCollection.get(i));
    }

    state.setCustomDataCollection(preparedCustomDataCollection);
    state.setCurrentCollection(currentCollection);

    close();
  }

  private void preview() {
    if (!hasStudentData()) {
      MessageWindow.show(gui, "Для предпросмотра сначала задайте все поля студента.");
      return;
    }

    Student student = studentBuilder.build();

    MessageWindow.show(gui, student.toString());
  }

  private boolean hasStudentData() {
    return groupNumber != null && averageGrade != null && recordBookNumber != null;
  }
}
