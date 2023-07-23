package org.example;

import org.example.exception.MyListException;

import java.util.Arrays;

public class StringList implements MyList<String> {
    private String[] array;

    public StringList(int arrayLength) {
        this.array = new String[arrayLength];
    }

    public StringList() {
        this.array = new String[0];
    }

    @Override
    public String add(String item) {
        if (item == null) {
            throw new MyListException("Нельзя добавить null");
        }
        String[] newArray = Arrays.copyOf(this.array, this.array.length + 1);
        newArray[this.array.length] = item;
        array = newArray;
        return item;
    }

    @Override
    public String add(int index, String item) {
        if (item == null) {
            throw new MyListException("Нельзя добавить null");
        }
        if (index >= array.length || index < 0) {
            throw new MyListException("Добавление элемента за пределы списка");
        }
        String[] newArray = new String[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, index);
        newArray[index] = item;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        array = newArray;
        return item;
    }

    @Override
    public String set(int index, String item) {
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
    public String remove(String item) {
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
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        array = newArray;
        return item;
    }

    @Override
    public String remove(int index) {
        if (index >= array.length || index < 0) {
            throw new MyListException("Удаление элемента за пределами списка");
        }
        String item = array[index];
        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        array = newArray;
        return item;
    }

    @Override
    public boolean contains(String item) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int indexOf(String item) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
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
                return Arrays.equals(((StringList) otherList).toArray(), array);
            }
        }
        return false;
    }

    @Override
    public long size() {
        return array.length;
    }

    @Override
    public boolean isEmpty() {
        return array.length == 0;
    }

    @Override
    public void clear() {
        array = new String[0];

    }

    @Override
    public String[] toArray() {
        return array;
    }
}
