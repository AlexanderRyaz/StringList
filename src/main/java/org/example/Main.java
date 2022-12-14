package org.example;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.example.IntegerList.*;

public class Main {
    public static void main(String[] args) {
        int[] b = IntStream.range(0, 100000).map(i -> ThreadLocalRandom.current().nextInt()).toArray();
        Integer[] a = Arrays.stream( b ).boxed().toArray( Integer[]::new );
        long start = System.currentTimeMillis();
        sortSelection(a);
        System.out.println("Сортировка выбором: " + (System.currentTimeMillis() - start));
        b = IntStream.range(0, 100000).map(i -> ThreadLocalRandom.current().nextInt()).toArray();
        a = Arrays.stream( b ).boxed().toArray( Integer[]::new );
        start = System.currentTimeMillis();
        sortBubble(a);
        System.out.println("Сортировка пузырьком: " + (System.currentTimeMillis() - start));
        b = IntStream.range(0, 100000).map(i -> ThreadLocalRandom.current().nextInt()).toArray();
        a = Arrays.stream( b ).boxed().toArray( Integer[]::new );
        start = System.currentTimeMillis();
        sortInsertion(a);
        System.out.println("Сортировка вставкой: " + (System.currentTimeMillis() - start));
    }
}