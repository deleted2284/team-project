public class MyLinkedList<T> implements MyList<T>
{
    private Node head;
    private int size;

    public void add(T value) {
        if (head == null) {
            this.head = new Node<>(value);
        } else {
            Node temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(new Node<>(value));
        }
        size++;
    }
    public T get(int index)
    {
        int currentIndex = 0;
        Node temp = head;
        while(temp != null)
        {
            if (currentIndex == index)
            {
                return (T) temp.getValue();

            }
            else {
                temp = temp.getNext();
                currentIndex++;
            }

        }
        throw new IllegalArgumentException();
    }
    public void remove(int index)
    {
        if (index == 0)
        {
            head = head.getNext();
            size--;
            return;

        }
        int currentIndex = 0;
        Node temp = head;
        while (temp != null)
        {
            if (currentIndex == index-1)
            {
                temp.setNext(temp.getNext().getNext());
                size--;
                return;
            }
            else {
                temp = temp.getNext();
                currentIndex++;
            }
        }
    }
    public T set(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException();
        }

        Node temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }

        T oldValue = (T) temp.getValue();
        temp.setValue(value);
        return oldValue;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty()
    {
        return size == 0;
    }
    public String toString() {
        Object[] res = new Object[size];
        int index = 0;
        Node temp = head;
        while (temp != null) {
            res[index] = temp.getValue();
            index++;
            temp = temp.getNext();
        }
        return Arrays.toString(res);
    }
    public static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public Node<T> getNext() {
            return next;
        }
    }


}