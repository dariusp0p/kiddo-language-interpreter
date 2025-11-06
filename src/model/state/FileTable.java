package model.state;

import model.adt.KiddoDictionary;
import model.value.StringValue;

import java.io.BufferedReader;

public interface FileTable {
    void define(StringValue filename, BufferedReader reader);
    boolean isDefined(StringValue filename);
    BufferedReader lookup(StringValue filename);
    void remove(StringValue filename);
    KiddoDictionary<StringValue, BufferedReader> getContent();
}
