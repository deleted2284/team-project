package ru.project.view;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;

public abstract class BaseView {

  protected final BasicWindow window;

  protected BaseView(String title, Component mainComponent) {
    window = new BasicWindow(title);
    window.setComponent(mainComponent);
  }

  public void show(WindowBasedTextGUI gui) {
    gui.addWindowAndWait(window);
  }

  public void close() {
    window.close();
  }

  protected void defaultAction() {
    close();
  }
}
