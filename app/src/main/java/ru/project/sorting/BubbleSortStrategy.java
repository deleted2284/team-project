package ru.project.sorting;

import ru.project.list.MyList;
import java.util.Comparator;

public class BubbleSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(MyList<T> list, Comparator<T> comparator) {
        if (list == null || list.size() <= 1) {
            return;
        }
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
}