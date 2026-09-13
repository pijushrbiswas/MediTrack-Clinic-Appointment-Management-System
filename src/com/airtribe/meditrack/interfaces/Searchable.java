package com.airtribe.meditrack.interfaces;

import java.util.List;

public interface Searchable<T> {
    List<T> search(String query);

    default boolean matchesIgnoreCase(String source, String query) {
        return source != null && query != null && source.toLowerCase().contains(query.toLowerCase());
    }
}
