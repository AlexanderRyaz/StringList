package org.example;

import org.example.exception.MyListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StringListTest {
    private StringList list;

    @BeforeEach
    public void setUp() {
        list = new StringList();
    }

    @Test
    void shouldAddItemToList() {
        assertTrue(list.contains(list.add("hello")));
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
        list.add("test");
        assertEquals(1, list.size());
    }

    @Test
    void shouldClearList() {
        list.add("test");
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
    void shouldAddItemToListAtIndex(int index, String item) {
        prepareList();
        Object add = list.add(index, item);
        Object expected = list.get(index);
        assertEquals(expected, add);
        assertEquals(4, list.size());
    }

    @ParameterizedTest
    @MethodSource("prepareDataForAddItemToListAtIndexWithError")
    void shouldFailByAddItemToListAtIndex(int index, String item, String expectedMessage) {
        prepareList();
        assertThrows(MyListException.class, () -> list.add(index, item), expectedMessage);
    }

    @ParameterizedTest
    @MethodSource("prepareDataForAddItemToListAtIndexWithError")
    void shouldFailBySetItem(int index, String item, String expectedMessage) {
        prepareList();
        assertThrows(MyListException.class, () -> list.set(index, item), expectedMessage);
    }

    @Test
    void shouldSetItemToList() {
        prepareList();
        list.set(0, "проверили");
        assertEquals(3, list.size());
        assertEquals(list.get(0), "проверили");
    }

    @ParameterizedTest
    @MethodSource("prepareDataForRemoveItemFromListWithError")
    void shouldRemoveItemFromListWithError(String item, String expectedErrorMessage) {
        prepareList();
        assertThrows(MyListException.class, () -> list.remove(item), expectedErrorMessage);
    }

    @Test
    void shouldRemoveItemFromEmptyListWithError() {
        assertThrows(MyListException.class, () -> list.remove("item"), "список пустой, удалять нечего");
    }

    @Test
    void shouldRemoveItemFromList() {
        prepareList();
        list.remove("метода");
        assertEquals(2, list.size());
        assertFalse(list.contains("метода"));
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
        list.remove(2);
        assertEquals(2, list.size());
        assertFalse(list.contains("метода"));
    }

    @ParameterizedTest
    @MethodSource("prepareDataForContainsItem")
    void shouldTestContainsItem(String item, boolean contains) {
        prepareList();
        boolean actualContains = list.contains(item);
        assertEquals(contains, actualContains);
    }

    @ParameterizedTest
    @MethodSource("prepareDataForIndexOf")
    void shouldTestIndexOf(String item, int expectedIndex) {
        prepareList();
        int actualIndex = list.indexOf(item);
        assertEquals(expectedIndex, actualIndex);
    }

    @ParameterizedTest
    @MethodSource("prepareDataForLastIndexOf")
    void shouldTestLastIndexOf(String item, int expectedIndex) {
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("a");
        list.add("d");
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
        assertEquals("проверяем", list.get(0));
    }

    @ParameterizedTest
    @MethodSource("prepareDataForEquals")
    void shouldTestEquals(MyList otherList, boolean expectedEquals) {
        list.add("c");
        list.add("d");
        boolean actualEquals = list.equals(otherList);
        assertEquals(expectedEquals, actualEquals);
    }

    private void prepareList() {
        list.add("проверяем");
        list.add("работу");
        list.add("метода");
    }


    public static Stream<Arguments> prepareDataForAddItemToListAtIndex() {
        return Stream.of(
                Arguments.of(0, "привет"),
                Arguments.of(2, "нашего")
        );
    }

    public static Stream<Arguments> prepareDataForAddItemToListAtIndexWithError() {
        return Stream.of(
                Arguments.of(0, null, "Нельзя добавить null"),
                Arguments.of(15, "abc", "Добавление элемента за пределы списка"),
                Arguments.of(-15, "abc", "Добавление элемента за пределы списка")
        );
    }

    public static Stream<Arguments> prepareDataForRemoveItemFromListWithError() {
        return Stream.of(
                Arguments.of(null, "Нельзя удалить null"),
                Arguments.of("abc", "элемент abc не найден")
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
                Arguments.of("работу", true),
                Arguments.of("привет", false),
                Arguments.of(null, false)
        );
    }

    public static Stream<Arguments> prepareDataForIndexOf() {
        return Stream.of(
                Arguments.of("работу", 1),
                Arguments.of("привет", -1),
                Arguments.of(null, -1)
        );
    }

    public static Stream<Arguments> prepareDataForLastIndexOf() {
        return Stream.of(
                Arguments.of("a", 3),
                Arguments.of("c", 2),
                Arguments.of("y", -1),
                Arguments.of(null, -1)
        );
    }

    public static Stream<Arguments> prepareDataForEquals() {
        StringList list1 = new StringList();
        list1.add("a");
        StringList list2 = new StringList();
        list2.add("a");
        list2.add("b");
        StringList list3 = new StringList();
        list3.add("c");
        list3.add("d");
        return Stream.of(
                Arguments.of(list1, false),
                Arguments.of(list2, false),
                Arguments.of(list3, true)
        );
    }
}
