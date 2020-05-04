package com.codeanalyzer;

import com.censusdata.CensusData;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class CodeAnalyzer {

    public static int loadStateData(String csvFileState){
        int count = 0;
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFileState));
            CsvToBean<CensusData> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CensusData.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            Iterator<CensusData> csvUserIterator = csvToBean.iterator();
            while (csvUserIterator.hasNext()) {
                count++;
                CensusData csvUser = csvUserIterator.next();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return count;
    }
}
