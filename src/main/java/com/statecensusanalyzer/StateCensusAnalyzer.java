package com.statecensusanalyzer;

import com.censusdata.CensusData;
import com.censusexception.CensusException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsvbuilder.OpenCSVBuilder;
import com.statecensus.StateCensus;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensusAnalyzer {
    int count;

    public int loadCensusData(String csvFilePath) throws CensusException {
        Iterator<StateCensus> csvUserIterator = null;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            csvUserIterator = this.getCSVFileIterator(reader, StateCensus.class);
            csvUserIterator = new OpenCSVBuilder().getCSVFileIterator(reader, StateCensus.class);
            while (csvUserIterator.hasNext()) {
                count++;
                csvUserIterator.next();
            }
            csvUserIterator = this.getCSVFileIterator(reader, StateCensus.class);
        } catch (NoSuchFileException exception) {
            throw new CensusException(CensusException.exceptionType.CENSUS_FILE_ERROR, "please enter proper file path or file type");
        } catch (RuntimeException exception) {
            throw new CensusException(CensusException.exceptionType.OTHER_FILE_ERROR, "please enter proper delimter  or proper header");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return getCount(csvUserIterator);
    }

    public int loadStateData(String csvFileState) throws CensusException {
        Iterator<StateCensus> csvUserIterator = null;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFileState));
            csvUserIterator = this.getCSVFileIterator(reader, CensusData.class);
            csvUserIterator = new OpenCSVBuilder().getCSVFileIterator(reader, CensusData.class);
            while (csvUserIterator.hasNext()) {
                count++;
                csvUserIterator.next();
            }
            csvUserIterator = this.getCSVFileIterator(reader, CensusData.class);
        } catch (NoSuchFileException exception) {
            throw new CensusException(CensusException.exceptionType.CENSUS_FILE_ERROR, "please enter proper file path or file type");
        } catch (RuntimeException exception) {
            throw new CensusException(CensusException.exceptionType.OTHER_FILE_ERROR, "please enter proper delimter  or proper header");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return getCount(csvUserIterator);
    }

    private <E> int getCount(Iterator<E> csvUserIterator) {
        int count = 0;
        while (csvUserIterator.hasNext()) {
            count++;
            csvUserIterator.next();
        }
        return count;
    }

    private <E> Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CensusException {
        try {
            CsvToBean<E> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(csvClass)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.iterator();
        } catch (RuntimeException exception) {
            throw new CensusException(CensusException.exceptionType.OTHER_FILE_ERROR, "please enter proper delimter  or proper header");
        }
    }
}




