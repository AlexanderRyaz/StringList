package org.example;

import java.util.function.Supplier;

public class StringListTest extends MyListTest<StringList>{
    public StringListTest() {
        super(StringList::new);
    }
}
