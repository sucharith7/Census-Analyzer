package com.opencsvbuilder;

import com.census.ICSVBuilder;
import com.censusexception.CensusException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;

public class OpenCSVBuilder <E> implements ICSVBuilder {
    public <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            CsvToBean<E> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(csvClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.iterator();
        } catch (RuntimeException exception) {
            throw new CSVBuilderException("please enter proper delimeter or proper header",CensusException.exceptionType.OTHER_FILE_ERROR);
        }
    }
}
