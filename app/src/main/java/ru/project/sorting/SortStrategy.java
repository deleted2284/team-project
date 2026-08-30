package ru.project.sorting;

import java.util.Comparator;
import ru.project.collection.MyList;

public interface SortStrategy<T> {
  void sort(MyList<T> list, Comparator<T> comparator);
}
