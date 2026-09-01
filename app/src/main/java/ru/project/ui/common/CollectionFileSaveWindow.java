package ru.project.ui.common;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import java.io.File;
import java.io.IOException;
import ru.project.collection.MyList;
import ru.project.io.StudentCsvWriter;
import ru.project.student.Student;

public class CollectionFileSaveWindow {

  private final WindowBasedTextGUI gui;
  private final MyList<Student> collection;

  public CollectionFileSaveWindow(WindowBasedTextGUI gui, MyList<Student> collection) {

    this.gui = gui;
    this.collection = collection;
  }

  public void showModal() {
    File selectedFile = selectFile();

    if (selectedFile == null) {
      return;
    }

    if (selectedFile.exists()) {
      saveToFile(selectedFile);
      return;
    }

    MessageDialogButton result =
        MessageDialog.showMessageDialog(
            gui,
            "Создание файла",
            "Файл не существует. Создать новый файл?",
            MessageDialogButton.Yes,
            MessageDialogButton.No);

    if (result == MessageDialogButton.Yes) {
      createAndSaveToFile(selectedFile);
    }
  }

  private File selectFile() {
    return new FileDialogBuilder()
        .setTitle("Сохранение коллекции")
        .setDescription("Выберите файл:")
        .setActionLabel("Сохранить")
        .build()
        .showDialog(gui);
  }

  private void createAndSaveToFile(File file) {
    try {
      if (!file.createNewFile()) {
        MessageWindow.showModal(gui, "Не удалось создать файл.");
        return;
      }

      saveToFile(file);

    } catch (IOException e) {
      MessageWindow.showModal(gui, "Не удалось создать файл.");
    }
  }

  private void saveToFile(File file) {
    try {
      StudentCsvWriter writer = new StudentCsvWriter(file.getAbsolutePath());

      for (int i = 0; i < collection.size(); i++) {
        writer.write(collection.get(i));
      }

      MessageWindow.showModal(gui, "Коллекция успешно сохранена.");

    } catch (IOException e) {
      MessageWindow.showModal(gui, "Не удалось сохранить коллекцию.");
    }
  }
}
