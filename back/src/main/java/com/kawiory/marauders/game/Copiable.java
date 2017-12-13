package com.kawiory.marauders.game;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author Kacper
 */
public interface Copiable<T> {

    T copy();

    static <E extends Copiable<E>> Map<String, E> copy(Map<String, E> mapToCopy) {
        return mapToCopy
                .entrySet()
                .stream()
                .collect(
                        toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().copy()
                        )
                );
    }

    static <E extends Copiable<E>> List<E> copy(List<E> listToCopy) {
        return listToCopy
                .stream()
                .map(element -> element.copy())
                .collect(toList());
    }
}
