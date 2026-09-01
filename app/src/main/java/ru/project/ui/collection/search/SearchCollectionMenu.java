package ru.project.ui.collection.search;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.model.AppState;
import ru.project.search.AverageGradeRangeCriteria;
import ru.project.search.CompositeStudentSearchCriteria;
import ru.project.search.GroupNumberRangeCriteria;
import ru.project.search.RecordBookNumberRangeCriteria;
import ru.project.search.SearchOperation;
import ru.project.search.StudentSearchCriteria;
import ru.project.search.StudentSearchService;
import ru.project.student.Student;
import ru.project.ui.base.BaseModalWindow;
import ru.project.ui.common.CollectionDisplayWindow;
import ru.project.ui.common.CollectionFileSaveWindow;
import ru.project.ui.common.MessageWindow;

public class SearchCollectionMenu extends BaseModalWindow {

  private final AppState state;

  private final SearchSettingsWindow searchSettingsWindow;

  private SearchSettings searchSettings;
  private MyList<Student> searchResultCollection;

  public SearchCollectionMenu(WindowBasedTextGUI gui, AppState state) {

    super("Меню поиска элемента в коллекции", gui);

    this.state = state;
    this.searchSettings = new SearchSettings();
    this.searchResultCollection = new MyLinkedList<>();

    this.searchSettingsWindow =
        new SearchSettingsWindow(gui, settings -> this.searchSettings = settings);

    ActionListBox menu = new ActionListBox();

    menu.addItem("Выполнить поиск", this::updateSearchResult);

    menu.addItem("Показать результат поиска", this::showSearchResult);

    menu.addItem("Сохранить результат поиска в файл", this::saveSearchResult);

    menu.addItem("Настроить процесс поиска...", searchSettingsWindow::showModal);

    menu.addItem("Выйти", this::close);

    setComponent(menu);
  }

  private void updateSearchResult() {
    MyList<Student> collection = state.getMainCollection();

    if (collection == null || collection.isEmpty()) {
      MessageWindow.showModal(gui, "Коллекция для поиска пуста.");
      return;
    }

    if (!hasEnabledFilters()) {
      MessageWindow.showModal(gui, "Не выбран ни один фильтр.");
      return;
    }

    if (!hasValidFilterRanges()) {
      return;
    }

    StudentSearchCriteria criteria = createSearchCriteria();

    StudentSearchService searchService = new StudentSearchService(criteria);

    searchResultCollection = searchService.find(collection);

    MessageWindow.showModal(gui, "Результат поиска обновлён.");
  }

  private StudentSearchCriteria createSearchCriteria() {
    SearchOperation operation =
        searchSettings.getRelation() == FilterRelation.AND
            ? SearchOperation.INTERSECTION
            : SearchOperation.UNION;

    CompositeStudentSearchCriteria compositeCriteria =
        new CompositeStudentSearchCriteria(operation);

    if (searchSettings.isGroupNumberFilterEnabled()) {
      compositeCriteria.add(
          new GroupNumberRangeCriteria(
              searchSettings.getMinGroupNumber(), searchSettings.getMaxGroupNumber()));
    }

    if (searchSettings.isAverageGradeFilterEnabled()) {
      compositeCriteria.add(
          new AverageGradeRangeCriteria(
              searchSettings.getMinAverageGrade(), searchSettings.getMaxAverageGrade()));
    }

    if (searchSettings.isRecordBookNumberFilterEnabled()) {
      compositeCriteria.add(
          new RecordBookNumberRangeCriteria(
              searchSettings.getMinRecordBookNumber(), searchSettings.getMaxRecordBookNumber()));
    }

    return compositeCriteria;
  }

  private boolean hasEnabledFilters() {
    return searchSettings.isGroupNumberFilterEnabled()
        || searchSettings.isAverageGradeFilterEnabled()
        || searchSettings.isRecordBookNumberFilterEnabled();
  }

  private boolean hasValidFilterRanges() {
    StringBuilder errors = new StringBuilder();

    if (!hasValidGroupNumberRange()) {
      errors.append("Для фильтра по номеру группы задайте минимальное и максимальное значения.\n");
    }

    if (!hasValidAverageGradeRange()) {
      errors.append("Для фильтра по среднему баллу задайте минимальное и максимальное значения.\n");
    }

    if (!hasValidRecordBookNumberRange()) {
      errors.append(
          "Для фильтра по номеру зачётной книжки задайте минимальное и максимальное значения.\n");
    }

    if (errors.length() > 0) {
      MessageWindow.showModal(gui, errors.toString());
      return false;
    }

    return true;
  }

  private boolean hasValidGroupNumberRange() {
    if (!searchSettings.isGroupNumberFilterEnabled()) {
      return true;
    }

    return searchSettings.getMinGroupNumber() != null && searchSettings.getMaxGroupNumber() != null;
  }

  private boolean hasValidAverageGradeRange() {
    if (!searchSettings.isAverageGradeFilterEnabled()) {
      return true;
    }

    return searchSettings.getMinAverageGrade() != null
        && searchSettings.getMaxAverageGrade() != null;
  }

  private boolean hasValidRecordBookNumberRange() {
    if (!searchSettings.isRecordBookNumberFilterEnabled()) {
      return true;
    }

    return searchSettings.getMinRecordBookNumber() != null
        && searchSettings.getMaxRecordBookNumber() != null;
  }

  private void showSearchResult() {
    if (searchResultCollection == null || searchResultCollection.isEmpty()) {

      MessageWindow.showModal(gui, "Результат поиска пуст.");
      return;
    }

    CollectionDisplayWindow window = new CollectionDisplayWindow(gui, searchResultCollection);

    window.showModal();
  }

  private void saveSearchResult() {
    if (searchResultCollection == null || searchResultCollection.isEmpty()) {

      MessageWindow.showModal(gui, "Результат поиска пуст.");
      return;
    }

    CollectionFileSaveWindow window = new CollectionFileSaveWindow(gui, searchResultCollection);

    window.showModal();
  }
}
