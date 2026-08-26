package ru.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataCollection {

  private final List<String> elements = new ArrayList<>();

  public void add(String element) {
    elements.add(element);
  }

  public void sort() {
    Collections.sort(elements);
  }

  public boolean contains(String element) {
    return elements.contains(element);
  }

  public int count(String element) {
    int count = 0;

    for (String current : elements) {
      if (current.equals(element)) {
        count++;
      }
    }

    return count;
  }

  public List<String> getElements() {
    return Collections.unmodifiableList(elements);
  }

  @Override
  public String toString() {
    if (elements.isEmpty()) {
      return "Коллекция пуста";
    }

    return String.join(", ", elements);
  }
}
