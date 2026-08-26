package ru.project.presenter;

import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.model.AppState;

public abstract class BasePresenter {

  protected final AppState state;
  protected final WindowBasedTextGUI gui;

  protected BasePresenter(AppState state, WindowBasedTextGUI gui) {
    this.state = state;
    this.gui = gui;
  }

  public abstract void start();
}
