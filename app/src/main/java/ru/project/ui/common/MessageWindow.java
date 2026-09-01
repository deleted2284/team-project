package ru.project.ui.common;

import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.ui.base.BaseModalWindow;

public class MessageWindow extends BaseModalWindow {

  public MessageWindow(WindowBasedTextGUI gui, String message) {
    super("Информация", gui);

    Panel panel = new Panel();
    panel.setLayoutManager(new LinearLayout());

    panel.addComponent(new Label(message));

    panel.addComponent(new Button("OK", this::close));

    setComponent(panel);
  }

  public static void showModal(WindowBasedTextGUI gui, String message) {
    new MessageWindow(gui, message).showModal();
  }
}
