import java.util.*;

/**
 * Типізована колекція, що реалізує інтерфейс Set.
 * Колекція побудована на основі масиву з початковою кількістю елементів 15
 * та збільшенням кількості елементів на 30% при досягненні ліміту.
 * 
 * @param <T> Тип елементів у колекції.
 */
public class CustomSet<T> implements Set<T> {

    private Object[] elements;
    private int size;
    private static final int INITIAL_CAPACITY = 15;
    private static final double CAPACITY_INCREMENT = 0.3;

    /**
     * Порожній конструктор. Ініціалізує колекцію з початковою кількістю елементів 15.
     */
    public CustomSet() {
        elements = new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Конструктор, що приймає один об'єкт.
     * 
     * @param item Об'єкт, який додається до колекції.
     */
    public CustomSet(T item) {
        this();
        add(item);
    }

    /**
     * Конструктор, що приймає стандартну колекцію об'єктів.
     * 
     * @param collection Колекція об'єктів, які додаються до нового CustomSet.
     */
    public CustomSet(Collection<? extends T> collection) {
        this();
        addAll(collection);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @SuppressWarnings("unchecked")
            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) elements[index++];
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[]) Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(T t) {
        if (contains(t)) {
            return false;
        }
        ensureCapacity();
        elements[size++] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(elements[i], o)) {
                System.arraycopy(elements, i + 1, elements, i, size - i - 1);
                elements[--size] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T item : c) {
            if (add(item)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            T item = it.next();
            if (!c.contains(item)) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object item : c) {
            if (remove(item)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        Arrays.fill(elements, 0, size, null);
        size = 0;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = (int) (elements.length * (1 + CAPACITY_INCREMENT));
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Set)) return false;
        Set<?> that = (Set<?>) o;
        return size == that.size() && containsAll(that);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (T item : this) {
            hash += (item == null ? 0 : item.hashCode());
        }
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // Виконавчий метод для тестування
    public static void main(String[] args) {
        CustomSet<String> set = new CustomSet<>();
        set.add("A");
        set.add("B");
        set.add("C");

        System.out.println("Initial set: " + set);

        set.remove("B");
        System.out.println("After removing B: " + set);

        CustomSet<String> set2 = new CustomSet<>(set);
        System.out.println("Copied set: " + set2);

        List<String> list = Arrays.asList("D", "E", "F");
        set.addAll(list);
        System.out.println("After adding list: " + set);
    }
}
