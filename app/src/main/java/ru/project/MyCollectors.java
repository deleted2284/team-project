package ru.test.maven.spring;
import java.util.stream.Collector;
public class MyCollectors
{
    private MyCollectors() {
    }
    public static <T> Collector<T, MyList<T>, MyList<T>> toMyList() {
        return new MyListCollector<>();
    }
}