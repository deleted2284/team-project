package ru.project.collectors;

import java.util.stream.Collector;
import ru.project.collection.MyList;

public class MyCollectors {
  private MyCollectors() {}

  public static <T> Collector<T, MyList<T>, MyList<T>> toMyList() {
    return new MyListCollector<>();
  }
}
