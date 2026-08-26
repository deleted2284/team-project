package ru.project.view;

public class FillMethodView extends ActionListView {

  private Runnable customAction = this::defaultAction;
  private Runnable randomAction = this::defaultAction;
  private Runnable fileAction = this::defaultAction;

  public FillMethodView() {
    super("Настроить способ заполнения");

    addItem("Пользовательские данные", () -> customAction.run());

    addItem("Случайные данные", () -> randomAction.run());

    addItem("Данные из файла", () -> fileAction.run());

    addItem("Выйти", this::close);
  }

  public void setCustomAction(Runnable action) {
    customAction = action;
  }

  public void setRandomAction(Runnable action) {
    randomAction = action;
  }

  public void setFileAction(Runnable action) {
    fileAction = action;
  }
}
