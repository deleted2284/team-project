package ru.project.ui.base;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

public abstract class BaseModalWindow extends BasicWindow {

  protected final WindowBasedTextGUI gui;

  protected BaseModalWindow(String title, WindowBasedTextGUI gui) {
    super(title);

    this.gui = gui;
  }

  public void showModal() {
    gui.addWindowAndWait(this);
  }
}
