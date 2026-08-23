package sorting;

import java.util.Comparator;

public interface SortStrategy<T> {
    void sort(MyList<T> list, Comparator<T> comparator);
}