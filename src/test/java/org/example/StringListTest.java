package org.example;

import org.example.exception.MyListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StringListTest {
    private StringList list;

    @BeforeEach
    public void setUp() {
        list = new StringList();
    }

    @Test
    void shouldAddItemToList() {
        String actualHello = list.add("hello");
        assertEquals(1, list.size());
        assertTrue(list.contains(actualHello));
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
    void shouldAddItemToListAtIndex(int index,String item, String[]expected) {
        list.add("проверяем");
        list.add("работу");
        list.add( "метода");
        String actualItem = list.add(index, item);
        assertEquals(4,list.size());
        assertEquals(list.get(index),actualItem);
        assertEquals(expected,list.toArray());
    }

    public static Stream<Arguments> prepareDataForAddItemToListAtIndex() {
        return Stream.of(
                Arguments.of(0, "привет", new String[]{"привет", "проверяем", "работу", "метода"}),
                Arguments.of(2, "нашего", new String[]{"проверяем", "работу", "нашего", "метода"})
        );
    }
}