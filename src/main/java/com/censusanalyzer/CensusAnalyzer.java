package com.censusanalyzer;

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

public class CensusAnalyzer {

    public static int loadCensusData(String csvFilePath) throws CensusException {
        int count = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBean<StateCensus> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(StateCensus.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<StateCensus> csvUserIterator = csvToBean.iterator();
            while (csvUserIterator.hasNext()) {
                count++;
                StateCensus csvUser = csvUserIterator.next();
            }
        } catch (NoSuchFileException exception) {
            throw new CensusException(CensusException.exceptionType.CENSUS_FILE_ERROR, "please enter proper file path or file type");
        }   catch (RuntimeException exception) {
            throw new CensusException(CensusException.exceptionType.OTHER_FILE_ERROR, "please enter proper delimter  or proper header");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
            return count;
    }
}



