package ru.project.sorting;

import ru.project.list.MyList;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToIntFunction;

public class EvenOnlyMergeSortStrategy<T> implements SortStrategy<T> {

    private final ToIntFunction<T> valueExtractor;

    public EvenOnlyMergeSortStrategy(ToIntFunction<T> valueExtractor) {
        this.valueExtractor = valueExtractor;
    }

    @Override
    public void sort(MyList<T> list, Comparator<T> comparator) {
        if (list == null || list.size() <= 1) {
            return;
        }

        List<Integer> evenIndices = new ArrayList<>();
        List<T> evenElements = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            int value = valueExtractor.applyAsInt(element);
            if (value % 2 == 0) {
                evenIndices.add(i);
                evenElements.add(element);
            }
        }

        if (evenElements.size() <= 1) {
            return;
        }

        List<T> sortedEven = mergeSort(evenElements, comparator);

        for (int j = 0; j < evenIndices.size(); j++) {
            list.set(evenIndices.get(j), sortedEven.get(j));
        }
    }

    private List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<T> left = mergeSort(list.subList(0, mid), comparator);
        List<T> right = mergeSort(list.subList(mid, list.size()), comparator);

        return merge(left, right, comparator);
    }

    private List<T> merge(List<T> left, List<T> right, Comparator<T> comparator) {
        List<T> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }

        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }

        return result;
    }
}