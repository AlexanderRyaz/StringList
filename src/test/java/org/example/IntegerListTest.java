package org.example;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class IntegerListTest extends MyListTest<IntegerList>{
    public IntegerListTest() {
        super(IntegerList::new);
    }
    @Test
    void sortInsertion(){
        Integer[]array=new Integer[]{1,5,4,3,2};
        IntegerList.sortInsertion(array);
        assertArrayEquals(new Integer[]{1,2,3,4,5},array);
    }
}
