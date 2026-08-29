package ru.project.ui.creation;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Supplier;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.model.AppState;
import ru.project.student.Student;
import ru.project.student.StudentBuilder;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.common.CollectionDisplayWindow;
import ru.project.ui.common.InputValueWindow;
import ru.project.ui.common.MessageWindow;

public class CustomDataSettingsWindow extends BaseModalWindow {

  private final AppState state;
  private final Supplier<Integer> requestedCollectionSize;
  private final StudentBuilder studentBuilder;

  private String groupNumber;
  private Double averageGrade;
  private Integer recordBookNumber;
  private Integer selectedObjectIndex;

  private final Label groupNumberLabel;
  private final Label averageGradeLabel;
  private final Label recordBookNumberLabel;
  private final Label indexLabel;

  private MyList<Student> preparedCustomDataCollection;

  public CustomDataSettingsWindow(
      WindowBasedTextGUI gui, AppState state, Supplier<Integer> collectionSize) {

    super("Настройка пользовательских данных", gui);

    this.state = state;
    this.requestedCollectionSize = collectionSize;

    this.studentBuilder = new StudentBuilder();

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

  @Override
  public void showModal() {
    prepareCustomDataCollection();
    super.showModal();
  }

  private void prepareCustomDataCollection() {
    preparedCustomDataCollection = new MyLinkedList<>();

    int targetCollectionSize = requestedCollectionSize.get();
    int currentCustomDataCollectionSize = state.getCustomDataCollection().size();

    int elementsToCopy = Math.min(currentCustomDataCollectionSize, targetCollectionSize);

    for (int i = 0; i < elementsToCopy; i++) {
      preparedCustomDataCollection.add(state.getCustomDataCollection().get(i));
    }

    for (int i = elementsToCopy; i < targetCollectionSize; i++) {
      preparedCustomDataCollection.add(null);
    }
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
                  MessageWindow.showModal(gui, "Индекс объекта не может быть отрицательным.");

                  return false;
                }

                int size = requestedCollectionSize.get();

                if (index >= size) {
                  MessageWindow.showModal(gui, "Индекс должен быть меньше размера коллекции.");

                  return false;
                }

                selectedObjectIndex = index;

                indexLabel.setText("Индекс объекта: " + selectedObjectIndex);

                return true;

              } catch (NumberFormatException e) {
                MessageWindow.showModal(gui, "Введите целое число.");

                return false;
              }
            });

    window.showModal();
  }

  private void editObject() {
    if (!hasStudentData()) {
      MessageWindow.showModal(gui, "Сначала задайте все поля студента.");
      return;
    }

    if (selectedObjectIndex == null) {
      MessageWindow.showModal(gui, "Сначала задайте индекс объекта.");
      return;
    }

    if (selectedObjectIndex >= preparedCustomDataCollection.size()) {
      MessageWindow.showModal(gui, "Индекс объекта выходит за границы подготовленной коллекции.");
      return;
    }

    Student student = studentBuilder.build();

    preparedCustomDataCollection.set(selectedObjectIndex, student);

    MessageWindow.showModal(gui, "Данные объекта успешно заданы.");
  }

  private void apply() {
    state.setCustomDataCollection(preparedCustomDataCollection);
    prepareCustomDataCollection();

    MessageWindow.showModal(gui, "Пользовательские данные успешно применены.");
  }

  private void preview() {
    if (preparedCustomDataCollection == null || preparedCustomDataCollection.isEmpty()) {

      MessageWindow.showModal(gui, "Подготовленная коллекция пуста.");

      return;
    }

    CollectionDisplayWindow window = new CollectionDisplayWindow(gui, preparedCustomDataCollection);

    window.showModal();
  }

  private boolean hasStudentData() {
    return groupNumber != null && averageGrade != null && recordBookNumber != null;
  }
}
