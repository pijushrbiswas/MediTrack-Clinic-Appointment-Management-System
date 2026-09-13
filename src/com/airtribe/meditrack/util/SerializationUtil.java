package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class SerializationUtil {
    private SerializationUtil() {
    }

    public static <T> void serialize(String path, T obj) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(obj);
        } catch (IOException e) {
            throw new InvalidDataException("Serialization failed", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String path) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            return (T) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new InvalidDataException("Deserialization failed", e);
        }
    }
}
