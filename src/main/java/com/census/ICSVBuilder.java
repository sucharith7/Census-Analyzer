package com.census;

import com.censusexception.CensusException;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder {
    public <E> Iterator<E> getCSVFileIterator(Reader reader,Class csvClass) throws CensusException;
}
