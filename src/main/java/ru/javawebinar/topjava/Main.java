package ru.javawebinar.topjava;

import ru.javawebinar.topjava.dao.inmemory.InMemory;
import ru.javawebinar.topjava.dao.inmemory.InMemoryImpl;


/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    private static final InMemory inMemory;

    static {
        inMemory = new InMemoryImpl();
    }

    public static void main(String[] args) {

    }

    private static void printMeals() {
        System.out.println();
        inMemory.getAllMeals().forEach(System.out::println);
        System.out.println();
    }
}
