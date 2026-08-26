package ru.project.view;


public class MainView extends ActionListView {

  private Runnable showCollectionAction = this::defaultAction;
  private Runnable createCollectionAction = this::defaultAction;
  private Runnable saveCollectionAction = this::defaultAction;
  private Runnable sortCollectionAction = this::defaultAction;
  private Runnable searchCollectionAction = this::defaultAction;
  private Runnable countOccurrencesAction = this::defaultAction;
  private Runnable exitAction = this::defaultAction;

  public MainView() {
    super("Главное меню");

    addItem("Показать текущую коллекцию", () -> showCollectionAction.run());

    addItem("Меню создания новой коллекции", () -> createCollectionAction.run());

    addItem("Меню сохранения текущей коллекции в файл", () -> saveCollectionAction.run());

    addItem("Меню сортировки текущей коллекции", () -> sortCollectionAction.run());

    addItem("Меню поиска элемента в текущей коллекции", () -> searchCollectionAction.run());

    addItem("Меню подсчёта количества вхождений элемента", () -> countOccurrencesAction.run());

    addItem("Завершить выполнение программы", () -> exitAction.run());
  }

  public void setShowCollectionAction(Runnable action) {
    showCollectionAction = action;
  }

  public void setCreateCollectionAction(Runnable action) {
    createCollectionAction = action;
  }

  public void setSaveCollectionAction(Runnable action) {
    saveCollectionAction = action;
  }

  public void setSortCollectionAction(Runnable action) {
    sortCollectionAction = action;
  }

  public void setSearchCollectionAction(Runnable action) {
    searchCollectionAction = action;
  }

  public void setCountOccurrencesAction(Runnable action) {
    countOccurrencesAction = action;
  }

  public void setExitAction(Runnable action) {
    exitAction = action;
  }
}
