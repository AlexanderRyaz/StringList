package org.example;

import java.util.function.Supplier;

public class IntegerListTest extends MyListTest<IntegerList>{
    public IntegerListTest() {
        super(IntegerList::new);
    }
}
