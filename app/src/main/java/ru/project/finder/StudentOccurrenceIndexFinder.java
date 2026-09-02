package ru.project.finder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import ru.project.collection.MyLinkedList;
import ru.project.collection.MyList;
import ru.project.student.Student;

public class StudentOccurrenceIndexFinder {
  public int[] findOccurrences(MyList<Student> students, Student target) {
    if (students == null) {
      throw new IllegalArgumentException();
    }
    if (target == null) {
      throw new IllegalArgumentException();
    }
    if (students.isEmpty()) {
      return new int[0];
    }
    int size = students.size();
    int threadCount = calculateThreadCount(size);
    int chunkSize = countChunkSize(size, threadCount);

    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    MyList<Future<MyList<Integer>>> futures = new MyLinkedList<>();

    for (int i = 0; i < threadCount; i++) {
      int fromIndex = i * chunkSize;
      int toIndex = Math.min(fromIndex + chunkSize, size);

      if (fromIndex >= toIndex) {
        break;
      }

      final int start = fromIndex;
      final int end = toIndex;

      Future<MyList<Integer>> future =
          executor.submit(
              () -> {
                return findInRange(students, target, start, end);
              });
      futures.add(future);
    }
    MyList<Integer> allIndices = new MyLinkedList<>();
    try {
      for (int i = 0; i < futures.size(); i++) {
        Future<MyList<Integer>> future = futures.get(i);
        MyList<Integer> partialResult = future.get();

        if (partialResult != null) {
          for (int j = 0; j < partialResult.size(); j++) {
            allIndices.add(partialResult.get(j));
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Ошибка при поиске", e);
    } finally {
      executor.shutdown();
    }

    sortList(allIndices);

    int[] resultArray = new int[allIndices.size()];
    for (int i = 0; i < allIndices.size(); i++) {
      resultArray[i] = allIndices.get(i);
    }

    return resultArray;
  }

  private void sortList(MyList<Integer> list) {
    if (list.size() <= 1) return;

    MyList<Integer> sorted = new MyLinkedList<>();

    for (int i = 0; i < list.size(); i++) {
      int value = list.get(i);
      int insertIndex = 0;
      while (insertIndex < sorted.size()) {
        if (sorted.get(insertIndex) > value) {
          break;
        }
        insertIndex++;
      }

      MyList<Integer> temp = new MyLinkedList<>();
      for (int k = 0; k < insertIndex; k++) {
        temp.add(sorted.get(k));
      }
      temp.add(value);
      for (int k = insertIndex; k < sorted.size(); k++) {
        temp.add(sorted.get(k));
      }
      sorted = temp;
    }

    while (!list.isEmpty()) {
      list.remove(0);
    }

    for (int i = 0; i < sorted.size(); i++) {
      list.add(sorted.get(i));
    }
  }

  private int calculateThreadCount(int size) {
    int processors = Runtime.getRuntime().availableProcessors();
    return Math.min(processors, size);
  }

  private int countChunkSize(int size, int threadCount) {
    if (threadCount == 0) return 0;
    return (size + threadCount - 1) / threadCount;
  }

  private MyList<Integer> findInRange(
      MyList<Student> students, Student target, int fromIndex, int toIndex) {
    MyList<Integer> foundIndices = new MyLinkedList<>();

    for (int i = fromIndex; i < toIndex; i++) {
      Student current = students.get(i);
      if (current != null && current.equals(target)) {
        foundIndices.add(i);
      }
    }

    return foundIndices;
  }
}
