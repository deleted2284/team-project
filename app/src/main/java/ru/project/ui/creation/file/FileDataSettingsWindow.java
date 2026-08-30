package ru.project.ui.creation.file;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.FileDialog;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import java.io.File;
import java.util.function.Consumer;
import ru.project.ui.base.BaseModalWindow;

public class FileDataSettingsWindow extends BaseModalWindow {

  // private final StudentBuilder studentBuilder;

  private final FileFillSettings settings;
  private final Consumer<FileFillSettings> onApply;

  private final Label filePathLabel;

  public FileDataSettingsWindow(WindowBasedTextGUI gui, Consumer<FileFillSettings> onApply) {

    super("Данные из файла", gui);

    this.onApply = onApply;

    this.settings = new FileFillSettings();
    // this.studentBuilder = new StudentBuilder();

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    filePathLabel = new Label("Абсолютный путь: " + getFilePathLabel());

    panel.addComponent(filePathLabel);

    panel.addComponent(new Button("Выбрать файл", this::selectFile));

    panel.addComponent(new Button("Применить", this::apply));

    panel.addComponent(new Button("Выйти", this::close));

    setComponent(panel);
  }

  private void selectFile() {
    FileDialog fileDialog =
        new FileDialogBuilder()
            .setTitle("Выбор файла")
            .setDescription("Выберите файл:")
            .setActionLabel("Открыть")
            .build();

    File selectedFile = fileDialog.showDialog(gui);

    if (selectedFile == null) {
      return;
    }

    settings.setAbsoluteFilePath(selectedFile.getAbsolutePath());

    filePathLabel.setText("Абсолютный путь: " + settings.getAbsoluteFilePath());
  }

  private void apply() {
    onApply.accept(settings);

    close();
  }

  private String getFilePathLabel() {
    String filePath = settings.getAbsoluteFilePath();

    return filePath == null || filePath.isBlank() ? "не задан" : filePath;
  }
}
