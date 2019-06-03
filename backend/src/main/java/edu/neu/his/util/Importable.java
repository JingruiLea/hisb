package edu.neu.his.util;


public interface Importable<T> {
    <T> void insert(T instance);
}
