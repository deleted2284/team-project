package ru.project.ui.creation.manual;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Consumer;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;
import ru.project.student.StudentBuilder;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.common.CollectionDisplayWindow;
import ru.project.ui.common.CollectionSizeInputWindow;
import ru.project.ui.common.InputValueWindow;
import ru.project.ui.common.MessageWindow;

public class ManualDataSettingsWindow extends BaseModalWindow {

  private final StudentBuilder studentBuilder;

  private Integer lastRequestedCollectionSize;
  private String lastRequestedGroupNumber;
  private Double lastRequestedAverageGrade;
  private Integer lastRequestedRecordBookNumber;
  private Integer lastRequestedSelectedObjectIndex;

  private final Label sizeLabel;
  private final Label groupNumberLabel;
  private final Label averageGradeLabel;
  private final Label recordBookNumberLabel;
  private final Label indexLabel;

  private MyList<Student> preparedCustomDataCollection;

  private final ManualFillSettings settings;
  private final Consumer<ManualFillSettings> onApply;

  public ManualDataSettingsWindow(WindowBasedTextGUI gui, Consumer<ManualFillSettings> onApply) {

    super("Настройка пользовательских данных", gui);

    this.onApply = onApply;

    this.settings = new ManualFillSettings();
    this.studentBuilder = new StudentBuilder();

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    sizeLabel = new Label("Размер: не задан");

    groupNumberLabel = new Label("Номер группы: не задан");

    averageGradeLabel = new Label("Средний балл: не задан");

    recordBookNumberLabel = new Label("Номер зачётной книжки: не задан");

    indexLabel = new Label("Индекс объекта: не задан");

    panel.addComponent(sizeLabel);
    panel.addComponent(groupNumberLabel);
    panel.addComponent(averageGradeLabel);
    panel.addComponent(recordBookNumberLabel);
    panel.addComponent(indexLabel);

    panel.addComponent(new Button("Задать размер", this::showCollectionSizeInputWindow));

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

  private void showCollectionSizeInputWindow() {
    CollectionSizeInputWindow window =
        new CollectionSizeInputWindow(
            gui, preparedCustomDataCollection.size(), this::handleCollectionSizeEntered);

    window.showModal();
  }

  private void handleCollectionSizeEntered(int size) {
    MyList<Student> resizedCollection = new MyLinkedList<>();

    int currentCollectionSize = preparedCustomDataCollection.size();

    int elementsToCopy = Math.min(currentCollectionSize, size);

    for (int i = 0; i < elementsToCopy; i++) {
      resizedCollection.add(preparedCustomDataCollection.get(i));
    }

    for (int i = elementsToCopy; i < size; i++) {
      resizedCollection.add(null);
    }

    preparedCustomDataCollection = resizedCollection;
    lastRequestedCollectionSize = size;

    if (lastRequestedSelectedObjectIndex != null
        && lastRequestedSelectedObjectIndex >= lastRequestedCollectionSize) {
      lastRequestedSelectedObjectIndex = null;
      indexLabel.setText("Индекс объекта: не задан");
    }

    sizeLabel.setText("Размер: " + lastRequestedCollectionSize);
  }

  @Override
  public void showModal() {
    prepareCustomDataCollection();

    super.showModal();
  }

  private void prepareCustomDataCollection() {
    MyList<Student> currentCustomDataCollection = settings.getCollection();

    preparedCustomDataCollection = new MyLinkedList<>();

    int currentCollectionSize = currentCustomDataCollection.size();

    int targetSize =
        lastRequestedCollectionSize == null ? currentCollectionSize : lastRequestedCollectionSize;

    int elementsToCopy = Math.min(currentCollectionSize, targetSize);

    for (int i = 0; i < elementsToCopy; i++) {
      preparedCustomDataCollection.add(currentCustomDataCollection.get(i));
    }

    for (int i = elementsToCopy; i < targetSize; i++) {
      preparedCustomDataCollection.add(null);
    }
  }

  private void setGroupNumber() {
    InputValueWindow window =
        new InputValueWindow(
            gui,
            "Номер группы",
            "Введите номер группы (например, A12):",
            lastRequestedGroupNumber,
            value -> {
              if (!value.matches(Student.getGroupNumberPattern())) {
                MessageWindow.showModal(gui, "Номер группы должен соответствовать формату A12.");
                return false;
              }

              lastRequestedGroupNumber = value;
              studentBuilder.setGroupNumber(value);

              groupNumberLabel.setText("Номер группы: " + lastRequestedGroupNumber);

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
            lastRequestedAverageGrade == null ? null : String.valueOf(lastRequestedAverageGrade),
            value -> {
              try {
                double parsedValue = Double.parseDouble(value);

                if (parsedValue < Student.getMinAverageGrade()
                    || parsedValue > Student.getMaxAverageGrade()) {

                  MessageWindow.showModal(gui, "Средний балл должен быть от 0.0 до 5.0.");

                  return false;
                }

                lastRequestedAverageGrade = parsedValue;

                studentBuilder.setAverageGrade(parsedValue);

                averageGradeLabel.setText("Средний балл: " + lastRequestedAverageGrade);

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
            lastRequestedRecordBookNumber == null
                ? null
                : String.valueOf(lastRequestedRecordBookNumber),
            value -> {
              try {
                int parsedValue = Integer.parseInt(value);

                if (parsedValue < Student.getMinRecordBookNumber()) {

                  MessageWindow.showModal(gui, "Номер зачётной книжки должен быть положительным.");

                  return false;
                }

                lastRequestedRecordBookNumber = parsedValue;

                studentBuilder.setRecordBookNumber(parsedValue);

                recordBookNumberLabel.setText(
                    "Номер зачётной книжки: " + lastRequestedRecordBookNumber);

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
            lastRequestedSelectedObjectIndex == null
                ? null
                : String.valueOf(lastRequestedSelectedObjectIndex),
            value -> {
              try {
                int index = Integer.parseInt(value);

                if (index < 0) {
                  MessageWindow.showModal(gui, "Индекс объекта не может быть отрицательным.");
                  return false;
                }

                if (index >= preparedCustomDataCollection.size()) {
                  MessageWindow.showModal(gui, "Индекс должен быть меньше размера коллекции.");
                  return false;
                }

                lastRequestedSelectedObjectIndex = index;

                indexLabel.setText("Индекс объекта: " + lastRequestedSelectedObjectIndex);

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

    if (lastRequestedSelectedObjectIndex == null) {
      MessageWindow.showModal(gui, "Сначала задайте индекс объекта.");
      return;
    }

    if (lastRequestedSelectedObjectIndex >= preparedCustomDataCollection.size()) {
      MessageWindow.showModal(gui, "Индекс объекта выходит за границы подготовленной коллекции.");
      return;
    }

    Student student = studentBuilder.build();

    preparedCustomDataCollection.set(lastRequestedSelectedObjectIndex, student);

    MessageWindow.showModal(gui, "Данные объекта успешно заданы.");
  }

  private void apply() {
    settings.setCollection(preparedCustomDataCollection);
    onApply.accept(settings);

    MessageWindow.showModal(gui, "Настройки создания коллекции вручную успешно применены.");
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
    return lastRequestedGroupNumber != null
        && lastRequestedAverageGrade != null
        && lastRequestedRecordBookNumber != null;
  }
}
