package ru.test.maven.spring;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class MyLinkedListTest
{
    @Test
    public void shouldTestAdd() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }
    @Test
    public void shouldTestGet()
    {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(10);
        assertEquals(10, list.get(0));
    }
    @Test
    public void shouldTestRemove() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.remove(0);

        assertEquals(2, list.size());
        assertEquals(20, list.get(0));
        assertEquals(30, list.get(1));
    }
    @Test
    void shouldTestSet() {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("A");
        list.add("B");
        list.add("C");

        String oldValue = list.set(1, "Z");

        assertEquals("B", oldValue);
        assertEquals("Z", list.get(1));
        assertEquals(3, list.size());

    }
    @Test
    void shouldTestSize()
    {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.size());
    }
    @Test
    void shouldTestisEmpty()
    {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        assertTrue(list.isEmpty());
    }
    @Test
    void shouldTestGet_OutOfBounds_ThrowsException() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(1);

        assertThrows(IllegalArgumentException.class, () -> list.get(-1));
        assertThrows(IllegalArgumentException.class, () -> list.get(1));   // index == size
        assertThrows(IllegalArgumentException.class, () -> list.get(5));   // index > size
    }
    @Test
    void shouldTestSet_OutOfBounds_ThrowsException() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.add(1);

        assertThrows(IllegalArgumentException.class, () -> list.set(-1, 99));
        assertThrows(IllegalArgumentException.class, () -> list.set(1, 99));
    }
    @Test
    void shouldTestRemove_IndexGreaterThanSize_ThrowsException() {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("man");
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(5));
    }
    @Test
    void shouldTestRemove_NegativeIndex_ThrowsException() {
        MyLinkedList<String> list = new MyLinkedList<>();
        list.add("1");
        list.add("2");

        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
    }
    @Test
    void shouldTestaddAll_nonEmptySource_toEmptyList_addsAllElementsInOrder() {
        MyList<String> list = new MyLinkedList<>();
        MyList<String> source = new MyLinkedList<>();
        source.add("A");
        source.add("B");
        source.add("C");

        list.addAll(source);

        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", list.get(2));
    }

    @Test
    void shouldTestaddAll_toNonEmptyList_appendsAtEnd() {
        MyList<String> list = new MyLinkedList<>();
        list.add("X");
        list.add("Y");

        MyList<String> source = new MyLinkedList<>();
        source.add("A");
        source.add("B");

        list.addAll(source);

        assertEquals(4, list.size());
        assertEquals("X", list.get(0));
        assertEquals("Y", list.get(1));
        assertEquals("A", list.get(2));
        assertEquals("B", list.get(3));
    }
}
