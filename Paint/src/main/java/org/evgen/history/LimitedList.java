package org.evgen.history;

import java.util.LinkedList;

public class LimitedList<T> {

    private final LinkedList<T> list = new LinkedList<>();
    private final int max;

    public LimitedList(int max) {
        this.max = max;
    }

    public void add(T e) {
        list.add(e);
        if (list.size() > max) {
            list.removeFirst();
        }
    }

    public int size() {
        return list.size();
    }

    public T getLast() {
        if (list.isEmpty()) return null;
        T e = list.getLast();
        list.removeLast();
        return e;
    }

}
