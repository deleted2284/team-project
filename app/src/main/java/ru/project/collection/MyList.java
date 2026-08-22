package ru.project.collection;

public interface MyList<T> {

  void add(T element);

  T get(int index);

  T set(int index, T element);

  T remove(int index);

  int size();

  boolean isEmpty();
}
