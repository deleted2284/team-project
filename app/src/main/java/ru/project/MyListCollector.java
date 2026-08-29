package ru.test.maven.spring;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
public class MyListCollector <T> implements Collector<T, MyList<T>, MyList<T>>
{
    @Override
    public Supplier<MyList<T>> supplier()
    {
        return () -> new MyLinkedList<>();
    }
    @Override
    public BiConsumer<MyList<T>, T> accumulator() {

        return (list, element) -> list.add(element);
    }
}