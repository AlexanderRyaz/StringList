package org.example;

import org.example.exception.MyListException;

import java.util.Arrays;

public class IntegerList implements MyList<Integer> {

    private Integer[] array;

    public IntegerList(int arrayLength) {
        this.array = new Integer[arrayLength];
    }

    public IntegerList() {
        this.array = new Integer[0];
    }

    @Override
    public Integer add(Integer item) {
        if (item == null) {
            throw new MyListException("Нельзя добавить null");
        }
        Integer[] newArray = Arrays.copyOf(this.array, this.array.length + 1);
        newArray[this.array.length] = item;
        array = newArray;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        if (item == null) {
            throw new MyListException("Нельзя добавить null");
        }
        if (index >= array.length || index < 0) {
            throw new MyListException("Добавление элемента за пределы списка");
        }
        Integer[] newArray = new Integer[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = item;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
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
        int index = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(item)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new MyListException("элемент " + item + " не найден");
        }
        Integer[] newArray = new Integer[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        array = newArray;
        return item;
    }

    @Override
    public Integer remove(int index) {
        if (index >= array.length || index < 0) {
            throw new MyListException("Удаление элемента за пределами списка");
        }
        Integer item = array[index];
        Integer[] newArray = new Integer[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        array = newArray;
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
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i].equals(item)) {
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
    public int size() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
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
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }

    public static boolean binarySearch(Integer[] arr, int element) {
        sortInsertion(arr);
        int min = 0;
        int max = arr.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

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
}

