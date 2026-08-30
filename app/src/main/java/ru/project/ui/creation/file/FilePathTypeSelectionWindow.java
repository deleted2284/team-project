package ru.project.ui.creation.file;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import java.util.function.Consumer;
import ru.project.ui.base.BaseModalWindow;

public class FilePathTypeSelectionWindow extends BaseModalWindow {

  private final Consumer<FilePathType> onPathTypeSelected;

  public FilePathTypeSelectionWindow(
      WindowBasedTextGUI gui, Consumer<FilePathType> onPathTypeSelected) {

    super("Выбор типа пути", gui);

    this.onPathTypeSelected = onPathTypeSelected;

    ActionListBox menu = new ActionListBox();

    menu.addItem("Абсолютный", () -> selectPathType(FilePathType.ABSOLUTE));

    menu.addItem("Относительный", () -> selectPathType(FilePathType.RELATIVE));

    menu.addItem("Выйти", this::close);

    setComponent(menu);
  }

  private void selectPathType(FilePathType pathType) {
    onPathTypeSelected.accept(pathType);
    close();
  }
}
