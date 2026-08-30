package ru.project.sorting;

import java.util.Comparator;
import ru.project.collection.MyList;

public class MergeSortStrategy<T> implements SortStrategy<T> {

  @Override
  public void sort(MyList<T> list, Comparator<T> comparator) {
    if (list == null || list.size() <= 1) {
      return;
    }
    mergeSort(list, 0, list.size() - 1, comparator);
  }

  private void mergeSort(MyList<T> list, int left, int right, Comparator<T> comparator) {
    if (left >= right) {
      return;
    }
    int mid = (left + right) / 2;
    mergeSort(list, left, mid, comparator);
    mergeSort(list, mid + 1, right, comparator);
    merge(list, left, mid, right, comparator);
  }

  private void merge(MyList<T> list, int left, int mid, int right, Comparator<T> comparator) {
    int leftSize = mid - left + 1;
    int rightSize = right - mid;

    @SuppressWarnings("unchecked")
    T[] leftArray = (T[]) new Object[leftSize];
    @SuppressWarnings("unchecked")
    T[] rightArray = (T[]) new Object[rightSize];

    for (int i = 0; i < leftSize; i++) {
      leftArray[i] = list.get(left + i);
    }
    for (int j = 0; j < rightSize; j++) {
      rightArray[j] = list.get(mid + 1 + j);
    }

    int i = 0, j = 0, k = left;

    while (i < leftSize && j < rightSize) {
      if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
        list.set(k, leftArray[i]);
        i++;
      } else {
        list.set(k, rightArray[j]);
        j++;
      }
      k++;
    }

    while (i < leftSize) {
      list.set(k, leftArray[i]);
      i++;
      k++;
    }
    while (j < rightSize) {
      list.set(k, rightArray[j]);
      j++;
      k++;
    }
  }
}
