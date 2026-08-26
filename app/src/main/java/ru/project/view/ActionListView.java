package ru.project.view;

import com.googlecode.lanterna.gui2.ActionListBox;

public class ActionListView extends BaseView {

  protected final ActionListBox actionList;

  public ActionListView(String title) {
    this(title, new ActionListBox());
  }

  private ActionListView(String title, ActionListBox actionList) {
    super(title, actionList);
    this.actionList = actionList;
  }

  public void addItem(String text, Runnable action) {
    actionList.addItem(text, action);
  }
}
