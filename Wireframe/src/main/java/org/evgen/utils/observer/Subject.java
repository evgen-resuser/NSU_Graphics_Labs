package org.evgen.utils.observer;

public interface Subject {
    void registerObserver(Observer observer);
    void notifyObservers();
}
