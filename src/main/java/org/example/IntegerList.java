package org.example;

import org.example.exception.MyListException;

import java.util.Arrays;
import java.util.Objects;

public class IntegerList implements MyList<Integer> {

    private Integer[] array;

    public IntegerList(int arrayLength) {
        this.array = new Integer[arrayLength];
    }

    public IntegerList() {
        this.array = new Integer[10];
    }

    @Override
    public Integer add(Integer item) {
        if (item == null) {
            throw new MyListException("Нельзя добавить null");
        }
        if (array.length == size()) {
            grow();
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = item;
                break;
            }
        }
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        if (item == null) {
            throw new MyListException("Нельзя добавить null");
        }
        int oldArrayLength = array.length;
        if (index >= oldArrayLength || index < 0) {
            throw new MyListException("Добавление элемента за пределы списка");
        }
        if (oldArrayLength == size()) {
            grow();
        }
        Integer[] newArray = Arrays.copyOf(array, oldArrayLength);
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = item;
        System.arraycopy(array, index, newArray, index + 1, (int) size() - index);
        array = newArray;
        return item;
    }

    @Override
    public Integer set(int index, Integer item) {
        if (item == null) {
            throw new MyListException("Нельзя добавить null");
        }
        if (index >= array.length || index < 0) {
            throw new MyListException("Добавление элемента за пределы списка");
        }
        array[index] = item;
        return item;
    }

    @Override
    public Integer remove(Integer item) {
        if (isEmpty()) {
            throw new MyListException("список пустой, удалять нечего");
        }
        if (item == null) {
            throw new MyListException("Нельзя удалить null");
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(item)) {
                array[i] = null;
                return item;
            }
        }
        throw new MyListException("элемент " + item + " не найден");
    }

    @Override
    public Integer remove(int index) {
        if (index >= array.length || index < 0) {
            throw new MyListException("Удаление элемента за пределами списка");
        }
        Integer item = array[index];
        array[index] = null;
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        if (item == null) {
            return false;
        }
        Integer[] integers = Arrays.copyOf(array, array.length);
        return binarySearch(integers, item);
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] != null && array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        if (index >= array.length || index < 0) {
            throw new MyListException("Индекс за пределами списка");
        }
        return array[index];
    }

    @Override
    public boolean equals(MyList otherList) {
        if (otherList == null) {
            throw new MyListException("сравнение с null");
        }
        if (otherList.getClass() == this.getClass()) {
            if (otherList.size() == this.size()) {
                return Arrays.equals(((IntegerList) otherList).toArray(), array);
            }
        }
        return false;
    }

    @Override
    public long size() {
        return Arrays.stream(array).filter(Objects::nonNull).count();

    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        array = new Integer[0];

    }

    @Override
    public Integer[] toArray() {
        return array;
    }

    //    Сортировка пузырьком: 20946
    public static void sortBubble(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swapElements(arr, j, j + 1);
                }
            }
        }
    }

    private static void swapElements(Integer[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

    //Сортировка выбором: 5181
    public static void sortSelection(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(arr, i, minElementIndex);
        }
    }

    //    Сортировка вставкой: 1022 - самая быстрая
    public static void sortInsertion(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Integer temp = arr[i];
            Integer j = i;
            if (temp == null) {
                continue;
            }
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }

    public static boolean binarySearch(Integer[] arr, Integer element) {
        quickSort(arr, 0, arr.length);
        Integer min = 0;
        Integer max = arr.length - 1;

        while (min <= max) {
            Integer mid = (min + max) / 2;

            if (element == arr[mid]) {
                return true;
            }

            if (element < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    private void grow() {
        array = Arrays.copyOf(array, (int) (array.length * 1.5));
    }

    private static int partition(Integer[] array, int begin, int end) {
        Integer p = array[end];
        int i = begin - 1;
        for (int j = begin; j < end; j++) {
            if (array[j] <= p) {
                i++;
                swapElements(array, i, j);
            }

        }
        swapElements(array, i + 1, end);
        return i + 1;
    }

    public static void quickSort(Integer[] array, int begin, int end) {
        if (begin < end) {
            int p = partition(array, begin, end);
            quickSort(array, begin, p - 1);
            quickSort(array, p + 1, end);
        }
    }
}

