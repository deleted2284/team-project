package ru.project.collectors;
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

    @Override
    public BinaryOperator<MyList<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<MyList<T>, MyList<T>> finisher()
    {
        return list -> list;
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return java.util.Set.of(
                Collector.Characteristics.IDENTITY_FINISH
        );
    }
}
