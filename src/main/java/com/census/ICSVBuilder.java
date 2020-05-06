package com.census;

import com.opencsvbuilder.CSVBuilderException;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder {
    public <E> Iterator<E> getCSVFileIterator(Reader reader,Class csvClass) throws CSVBuilderException;
}
