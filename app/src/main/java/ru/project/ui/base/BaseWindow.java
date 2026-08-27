package ru.project.ui.base;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

public abstract class BaseWindow extends BasicWindow {

  protected final WindowBasedTextGUI gui;

  protected BaseWindow(String title, WindowBasedTextGUI gui) {
    super(title);

    this.gui = gui;
  }

  public void show() {
    gui.addWindowAndWait(this);
  }
}
