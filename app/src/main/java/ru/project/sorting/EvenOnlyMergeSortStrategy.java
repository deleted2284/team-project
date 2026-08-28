package ru.project.sorting;

import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;

import java.util.Comparator;
import java.util.function.ToIntFunction;

public class EvenOnlyMergeSortStrategy<T> implements SortStrategy<T> {
    private final ToIntFunction<T> valueExtractor;
    private final SortStrategy<T> mergeSortStrategy;

    public EvenOnlyMergeSortStrategy(ToIntFunction<T> valueExtractor) {
        this.valueExtractor = valueExtractor;
        this.mergeSortStrategy = new MergeSortStrategy<>();
    }

    @Override
    public void sort(MyList<T> list, Comparator<T> comparator) {

        if (list == null || list.size() <= 1) {
            return;
        }

        int evenCount = 0;

        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);

            if (valueExtractor.applyAsInt(element) % 2 == 0) {
                evenCount++;
            }
        }
        if (evenCount <= 1) {
            return;
        }

        MyList<T> evenElements = new MyLinkedList<>();

        int[] evenIndexes = new int[evenCount];

        int evenIndex = 0;

        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);

            if (valueExtractor.applyAsInt(element) % 2 == 0) {
                evenElements.add(element);
                evenIndexes[evenIndex] = i;
                evenIndex++;
            }
        }
        mergeSortStrategy.sort(evenElements, comparator);

        for (int i = 0; i < evenIndexes.length; i++) {
            list.set(evenIndexes[i], evenElements.get(i));
        }
    }
}

