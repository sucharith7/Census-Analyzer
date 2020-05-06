package com.statecensusanalyzer;

import com.census.ICSVBuilder;
import com.censusdata.CensusData;
import com.censusexception.CensusException;
import com.censusexception.CensusException.exceptionType;
import com.csvbuilderfactory.CSVBuilderFactory;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsvbuilder.CSVBuilderException;
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
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCensus> censusIterator = csvBuilder.getCSVFileIterator(reader, StateCensus.class);
            return this.getCount(censusIterator);
        } catch (NoSuchFileException exception) {
            throw new CensusException(CensusException.exceptionType.CENSUS_FILE_ERROR, "please enter proper file path or file type");
        } catch (RuntimeException exception) {
            throw new CensusException(CensusException.exceptionType.OTHER_FILE_ERROR, "please enter proper delimter  or proper header");
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (CSVBuilderException exception) {
            exception.printStackTrace();
        }
        return count;
    }


    public int loadStateData(String csvFileState) throws CensusException {
        Iterator<StateCensus> csvUserIterator = null;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFileState));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CensusData> censusIterator = csvBuilder.getCSVFileIterator(reader, CensusData.class);
            return this.getCount(censusIterator);
        } catch (NoSuchFileException exception) {
            throw new CensusException(exceptionType.CENSUS_FILE_ERROR, "please enter proper file path or file type");
        } catch (RuntimeException exception) {
            throw new CensusException(exceptionType.OTHER_FILE_ERROR, "please enter proper delimeter or proper header");
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (CSVBuilderException exception) {
            exception.printStackTrace();
        }
        return count;
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
            throw new CensusException(exceptionType.OTHER_FILE_ERROR, "please enter proper delimter  or proper header");
        }
    }
}




