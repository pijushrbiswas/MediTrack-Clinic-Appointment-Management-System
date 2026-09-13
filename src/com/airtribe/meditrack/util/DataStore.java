package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.MedicalEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;
import java.util.Optional;

public class DataStore<T extends MedicalEntity> implements Iterable<T> {
    private final Map<String, T> data = new HashMap<>();

    public synchronized void save(T entity) {
        data.put(entity.getId(), entity);
    }

    public synchronized Optional<T> findById(String id) {
        return Optional.ofNullable(data.get(id));
    }

    public synchronized List<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public synchronized void delete(String id) {
        data.remove(id);
    }

    public synchronized int size() {
        return data.size();
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return findAll().iterator();
    }
}
