package ru.project.sorting;

import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;

import java.util.Comparator;
import java.util.function.ToIntFunction;

public class EvenOnlySortStrategy<T> implements SortStrategy<T> {

    private SortStrategy<T> sortStrategy;
    private ToIntFunction<T> valueExtractor;

    public EvenOnlySortStrategy() {
    }

    public EvenOnlySortStrategy(SortStrategy<T> sortStrategy) {
        setSortStrategy(sortStrategy);
    }

    public EvenOnlySortStrategy(
            SortStrategy<T> sortStrategy,
            ToIntFunction<T> valueExtractor
    ) {
        setSortStrategy(sortStrategy);
        setValueExtractor(valueExtractor);
    }

    public void setSortStrategy(SortStrategy<T> sortStrategy) {
        if (sortStrategy == null) {
            throw new IllegalArgumentException("Sort strategy must not be null");
        }

        this.sortStrategy = sortStrategy;
    }

    public void setValueExtractor(ToIntFunction<T> valueExtractor) {
        if (valueExtractor == null) {
            throw new IllegalArgumentException("Value extractor must not be null");
        }

        this.valueExtractor = valueExtractor;
    }

    @Override
    public void sort(MyList<T> list, Comparator<T> comparator) {
        if (sortStrategy == null) {
            throw new IllegalStateException("Sort strategy is not set");
        }

        if (valueExtractor == null) {
            throw new IllegalStateException("Value extractor is not set");
        }

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

        sortStrategy.sort(evenElements, comparator);

        for (int i = 0; i < evenIndexes.length; i++) {
            list.set(evenIndexes[i], evenElements.get(i));
        }
    }
}