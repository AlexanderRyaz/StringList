package org.example;

import org.example.exception.MyListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class IntegerListTest {
    private IntegerList list;

    @BeforeEach
    public void setUp() {
        list = new IntegerList();
    }

    @Test
    void shouldAddItemToList() {
        assertTrue(list.contains(list.add(128)));
        assertEquals(1, list.size());
    }

    @Test
    void shouldNotAddItemToList() {
        assertThrows(MyListException.class, () -> list.add(null), "Нельзя добавить null");
        assertTrue(list.isEmpty());
    }

    @Test
    void shouldGetListSize() {
        assertTrue(list.isEmpty());
        list.add(33);
        assertEquals(1, list.size());
    }

    @Test
    void shouldClearList() {
        list.add(35);
        assertEquals(1, list.size());
        list.clear();
        assertTrue(list.isEmpty());
    }

    @Test
    void shouldTestIsEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @ParameterizedTest
    @MethodSource("prepareDataForAddItemToListAtIndex")
    void shouldAddItemToListAtIndex(int index, Integer item) {
        prepareList();
        Object add = list.add(index, item);
        Object expected = list.get(index);
        assertEquals(expected, add);
        assertEquals(4, list.size());
    }

    @ParameterizedTest
    @MethodSource("prepareDataForAddItemToListAtIndexWithError")
    void shouldFailByAddItemToListAtIndex(int index, Integer item, String expectedMessage) {
        prepareList();
        assertThrows(MyListException.class, () -> list.add(index, item), expectedMessage);
    }

    @ParameterizedTest
    @MethodSource("prepareDataForAddItemToListAtIndexWithError")
    void shouldFailBySetItem(int index, Integer item, String expectedMessage) {
        prepareList();
        assertThrows(MyListException.class, () -> list.set(index, item), expectedMessage);
    }

    @Test
    void shouldSetItemToList() {
        prepareList();
        list.set(0, 33);
        assertEquals(3, list.size());
        assertEquals(list.get(0), 33);
    }

    @ParameterizedTest
    @MethodSource("prepareDataForRemoveItemFromListWithError")
    void shouldRemoveItemFromListWithError(Integer item, String expectedErrorMessage) {
        prepareList();
        assertThrows(MyListException.class, () -> list.remove(item), expectedErrorMessage);
    }

    @Test
    void shouldRemoveItemFromEmptyListWithError() {
        assertThrows(MyListException.class, () -> list.remove(-1000), "список пустой, удалять нечего");
    }

    @Test
    void shouldRemoveItemFromList() {
        prepareList();
        list.remove(1);
        assertEquals(2, list.size());
        assertFalse(list.contains(23));
    }

    @ParameterizedTest
    @MethodSource("prepareDataForRemoveItemFromListAtIndexWithError")
    void shouldRemoveItemFromListAtIndexWithError(int index, String expectedErrorMessage) {
        prepareList();
        assertThrows(MyListException.class, () -> list.remove(index), expectedErrorMessage);
    }

    @Test
    void shouldRemoveItemAtIndexFromList() {
        prepareList();
        list.remove(0);
        assertEquals(2, list.size());
        assertFalse(list.contains(2));
    }

    @ParameterizedTest
    @MethodSource("prepareDataForContainsItem")
    void shouldTestContainsItem(Integer item, boolean contains) {
        prepareList();
        boolean actualContains = list.contains(item);
        assertEquals(contains, actualContains);
    }

    @ParameterizedTest
    @MethodSource("prepareDataForIndexOf")
    void shouldTestIndexOf(Integer item, int expectedIndex) {
        prepareList();
        int actualIndex = list.indexOf(item);
        assertEquals(expectedIndex, actualIndex);
    }

    @ParameterizedTest
    @MethodSource("prepareDataForLastIndexOf")
    void shouldTestLastIndexOf(Integer item, int expectedIndex) {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(2);
        list.add(1);
        int actualIndex = list.lastIndexOf(item);
        assertEquals(expectedIndex, actualIndex);
    }

    @Test
    void shouldTestGetWithError() {
        prepareList();
        assertThrows(MyListException.class, () -> list.get(18), "Индекс за пределами списка");
    }

    @Test
    void shouldTestGet() {
        prepareList();
        assertEquals(2, list.get(0));
    }

    @ParameterizedTest
    @MethodSource("prepareDataForEquals")
    void shouldTestEquals(MyList otherList, boolean expectedEquals) {
        list.add(3);
        list.add(4);
        boolean actualEquals = list.equals(otherList);
        assertEquals(expectedEquals, actualEquals);
    }

    private void prepareList() {
        list.add(2);
        list.add(23);
        list.add(-1000);
    }


    public static Stream<Arguments> prepareDataForAddItemToListAtIndex() {
        return Stream.of(
                Arguments.of(0, 18),
                Arguments.of(2, 38)
        );
    }

    public static Stream<Arguments> prepareDataForAddItemToListAtIndexWithError() {
        return Stream.of(
                Arguments.of(0, null, "Нельзя добавить null"),
                Arguments.of(15, 38, "Добавление элемента за пределы списка"),
                Arguments.of(-15, 45, "Добавление элемента за пределы списка")
        );
    }

    public static Stream<Arguments> prepareDataForRemoveItemFromListWithError() {
        return Stream.of(
                Arguments.of(null, "Нельзя удалить null"),
                Arguments.of(138, "элемент abc не найден")
        );
    }

    public static Stream<Arguments> prepareDataForRemoveItemFromListAtIndexWithError() {
        return Stream.of(
                Arguments.of(15, "Удаление элемента за пределами списка"),
                Arguments.of(-15, "Удаление элемента за пределами списка")
        );
    }

    public static Stream<Arguments> prepareDataForContainsItem() {
        return Stream.of(
                Arguments.of(-1000, true),
                Arguments.of(94, false),
                Arguments.of(null, false)
        );
    }

    public static Stream<Arguments> prepareDataForIndexOf() {
        return Stream.of(
                Arguments.of(23, 1),
                Arguments.of(120, -1),
                Arguments.of(null, -1)
        );
    }

    public static Stream<Arguments> prepareDataForLastIndexOf() {
        return Stream.of(
                Arguments.of(1, 4),
                Arguments.of(3, 2),
                Arguments.of(58, -1),
                Arguments.of(null, -1)
        );
    }

    public static Stream<Arguments> prepareDataForEquals() {
        IntegerList list1 = new IntegerList();
        list1.add(5);
        IntegerList list2 = new IntegerList();
        list2.add(8);
        list2.add(6);
        IntegerList list3 = new IntegerList();
        list3.add(3);
        list3.add(4);
        return Stream.of(
                Arguments.of(list1, false),
                Arguments.of(list2, false),
                Arguments.of(list3, true)
        );
    }

    @Test
    void sortInsertion() {
        Integer[] array = new Integer[]{1, 5, 4, 3, 2};
        IntegerList.sortInsertion(array);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, array);
    }
}
