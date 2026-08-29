package ru.project.ui.window;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.ui.base.BaseModalWindow;

public class ParitySortSettingsWindow extends BaseModalWindow {

  public ParitySortSettingsWindow(WindowBasedTextGUI gui) {
    super("Настройка сортировки по чётности", gui);

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    panel.addComponent(new Label("Сортировка выполняется по номеру зачётной книжки."));

    panel.addComponent(new Button("Назад", this::close));

    setComponent(panel);
  }
}
