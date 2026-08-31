package ru.test.maven.spring;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.Collections;

public class MyListCollectorTest
{
    private final MyListCollector<Integer> collector = new MyListCollector<>();

    @Test
    void shouldTestCollectInOrderSequential() {
        MyList<Integer> result = IntStream.range(0, 10)
                .boxed()
                .collect(MyCollectors.toMyList());

        assertEquals(10, result.size());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(i, (int) result.get(i));
        }

    }

    @Test
    void shouldTestCollectInOrderParallel() {
        MyList<Integer> result = IntStream.range(0, 100)
                .parallel()
                .boxed()
                .collect(MyCollectors.toMyList());

        assertEquals(100, result.size());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(i, (int) result.get(i));
        }
    }

    @Test
    void shouldTestHandleEmptyStream() {
        MyList<Integer> result = IntStream.empty()
                .boxed()
                .collect(MyCollectors.toMyList());

        assertTrue(result.isEmpty());

    }
    @Test
    void shouldTestNotUseStandardList() {
        MyList<String> result = IntStream.range(0, 5)
                .mapToObj(i -> "item" + i)
                .collect(MyCollectors.toMyList());

        assertInstanceOf(MyList.class, result);
        assertFalse(result instanceof java.util.List);
    }
    @Test
    void shouldTestHaveCorrectCharacteristics() {
        Set<Collector.Characteristics> characteristics = collector.characteristics();
        assertTrue(characteristics.contains(java.util.stream.Collector.Characteristics.IDENTITY_FINISH));
        assertFalse(characteristics.contains(java.util.stream.Collector.Characteristics.UNORDERED));
    }

}
