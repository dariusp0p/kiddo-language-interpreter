package model.state;

import exceptions.AdtException;
import model.adt.KiddoDictionary;
import model.adt.KiddoHashMapDictionary;
import model.value.StringValue;

import java.io.BufferedReader;

public class MapFileTable implements FileTable {
    private final KiddoDictionary<StringValue, BufferedReader> table =
            new KiddoHashMapDictionary<>();

    @Override
    public void define(StringValue filename, BufferedReader reader) throws AdtException {
        if (invalidFilename(filename)) throw new AdtException("Invalid filename");
        if (invalidReader(reader)) throw new AdtException("Invalid reader");
        if (table.containsKey(filename)) throw new AdtException("File already opened: " + filename.value());
        table.put(filename, reader);
    }

    @Override
    public boolean isDefined(StringValue filename) throws AdtException {
        if (invalidFilename(filename)) throw new AdtException("Invalid filename");
        return table.containsKey(filename);
    }

    @Override
    public BufferedReader lookup(StringValue filename) throws AdtException {
        if (invalidFilename(filename)) throw new AdtException("Invalid filename");
        return table.get(filename); // dictionary throws if not opened
    }

    @Override
    public void remove(StringValue filename) throws AdtException {
        if (invalidFilename(filename)) throw new AdtException("Invalid filename");
        if (table.containsKey(filename)) throw new AdtException("File already opened: " + filename.value());
        table.remove(filename);
    }

    @Override
    public KiddoDictionary<StringValue, BufferedReader> getContent() {
        return table;
    }

    @Override
    public String toString() {
        return table.keySet().toString();
    }

    private boolean invalidFilename(StringValue filename) {
        return (filename == null);
    }

    private boolean invalidReader(BufferedReader reader) {
        return (reader == null);
    }
}
