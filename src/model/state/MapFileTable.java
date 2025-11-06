package model.state;

import model.adt.KiddoDictionary;
import model.adt.KiddoHashMapDictionary;
import model.value.StringValue;
import utilities.KiddoException;

import java.io.BufferedReader;

public class MapFileTable implements FileTable {
    private final KiddoDictionary<StringValue, BufferedReader> table = new KiddoHashMapDictionary<>();

    @Override
    public void define(StringValue filename, BufferedReader reader) {
        if (filename == null || reader == null) {
            throw new IllegalArgumentException("Filename and reader must be non-null.");
        }
        if (table.containsKey(filename)) {
            throw new KiddoException("File already opened: " + filename);
        }
        table.put(filename, reader);
    }

    @Override
    public boolean isDefined(StringValue filename) {
        return table.containsKey(filename);
    }

    @Override
    public BufferedReader lookup(StringValue filename) {
        BufferedReader br = table.get(filename);
        if (br == null) {
            throw new KiddoException("File not opened: " + filename);
        }
        return br;
    }

    @Override
    public void remove(StringValue filename) {
        if (!table.containsKey(filename)) {
            throw new KiddoException("Cannot remove unopened file: " + filename);
        }
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
}
