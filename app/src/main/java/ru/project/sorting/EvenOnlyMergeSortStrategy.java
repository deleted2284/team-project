package ru.project.sorting;

import ru.project.collection.MyList;

import java.util.Comparator;
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

        @SuppressWarnings("unchecked")
        T[] evenElements = (T[]) new Object[evenCount];

        int[] evenIndexes = new int[evenCount];

        int evenIndex = 0;

        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);

            if (valueExtractor.applyAsInt(element) % 2 == 0) {
                evenElements[evenIndex] = element;
                evenIndexes[evenIndex] = i;
                evenIndex++;
            }
        }
        mergeSort(evenElements, 0, evenElements.length - 1, comparator);

        for (int i = 0; i < evenIndexes.length; i++) {
            list.set(evenIndexes[i], evenElements[i]);
        }
    }

    private void mergeSort(
            T[] elements,
            int left,
            int right,
            Comparator<T> comparator
    ) {
        if (left >= right) {
            return;
        }

        int mid = (left + right) / 2;

        mergeSort(elements, left, mid, comparator);
        mergeSort(elements, mid + 1, right, comparator);

        merge(elements, left, mid, right, comparator);
    }
    private void merge(
            T[] elements,
            int left,
            int mid,
            int right,
            Comparator<T> comparator
    ) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        @SuppressWarnings("unchecked")
        T[] leftArray = (T[]) new Object[leftSize];

        @SuppressWarnings("unchecked")
        T[] rightArray = (T[]) new Object[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = elements[left + i];
        }

        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = elements[mid + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftSize && j < rightSize) {
            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                elements[k] = leftArray[i];
                i++;
            } else {
                elements[k] = rightArray[j];
                j++;
            }

            k++;
        }

        while (i < leftSize) {
            elements[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < rightSize) {
            elements[k] = rightArray[j];
            j++;
            k++;
        }
    }
}

