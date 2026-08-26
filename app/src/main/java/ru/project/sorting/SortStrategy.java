package ru.project.sorting;

import ru.project.collection.MyList;

import java.util.Comparator;

public interface SortStrategy<T> {
    void sort(MyList<T> list, Comparator<T> comparator);
}