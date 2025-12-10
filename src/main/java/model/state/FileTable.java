package model.state;

import exceptions.AdtException;
import model.adt.KiddoDictionary;
import model.value.StringValue;

import java.io.BufferedReader;

public interface FileTable {
    void define(StringValue filename, BufferedReader reader) throws AdtException;
    boolean isDefined(StringValue filename) throws AdtException;
    BufferedReader lookup(StringValue filename) throws AdtException;
    void remove(StringValue filename) throws AdtException;
    KiddoDictionary<StringValue, BufferedReader> getContent();
}