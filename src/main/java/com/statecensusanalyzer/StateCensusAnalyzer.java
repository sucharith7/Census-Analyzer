package com.statecensusanalyzer;

import com.censusdata.CensusData;
import com.censusexception.CensusException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.statecensus.StateCensus;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Iterator;

public class StateCensusAnalyzer {

    public int loadCensusData(String csvFilePath) throws CensusException {
        int count = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            Iterator<StateCensus> csvUserIterator = this.getCSVFileIterator(reader, StateCensus.class);
            while (csvUserIterator.hasNext()) {
                count++;
                StateCensus csvUser = csvUserIterator.next();
            }
        } catch (NoSuchFileException exception) {
            throw new CensusException(CensusException.exceptionType.CENSUS_FILE_ERROR, "please enter proper file path or file type");
        } catch (RuntimeException exception) {
            throw new CensusException(CensusException.exceptionType.OTHER_FILE_ERROR, "please enter proper delimter  or proper header");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return count;
    }

    public int loadStateData(String csvFileState) throws CensusException {
        int count = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFileState));
            Iterator<CensusData> csvUserIterator = this.getCSVFileIterator(reader, CensusData.class);
            while (csvUserIterator.hasNext()) {
                count++;
                CensusData csvUser = csvUserIterator.next();
            }
        } catch (NoSuchFileException exception) {
            throw new CensusException(CensusException.exceptionType.CENSUS_FILE_ERROR, "please enter proper file path or file type");
        } catch (RuntimeException exception) {
            throw new CensusException(CensusException.exceptionType.OTHER_FILE_ERROR, "please enter proper delimter  or proper header");
        } catch (IOException exception) {
            exception.printStackTrace();
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




