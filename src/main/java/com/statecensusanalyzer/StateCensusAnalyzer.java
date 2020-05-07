package com.statecensusanalyzer;

import com.census.ICSVBuilder;
import com.censusdata.CensusData;
import com.censusexception.CensusException;
import com.censusexception.CensusException.exceptionType;
import com.csvbuilderfactory.CSVBuilderFactory;
import com.google.gson.Gson;
import com.opencsvbuilder.CSVBuilderException;
import com.statecensus.StateCensus;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

public class StateCensusAnalyzer {

    List<StateCensus> csvFileList = null;

    public int loadCensusData(String csvFilePath) throws CensusException {

        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<StateCensus> csvFileList = csvBuilder.getCSVFileList(reader, StateCensus.class);
            return csvFileList.size();
        } catch (NoSuchFileException exception) {
            throw new CensusException(CensusException.exceptionType.CENSUS_FILE_ERROR, "please enter proper file path or file type");
        } catch (RuntimeException exception) {
            throw new CensusException(CensusException.exceptionType.OTHER_FILE_ERROR, "please enter proper delimter  or proper header");
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (CSVBuilderException exception) {
            exception.printStackTrace();
        }
        return 0;
    }


    public int loadStateData(String csvFileState) throws CensusException {

        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFileState));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CensusData> csvFileList = csvBuilder.getCSVFileList(reader, CensusData.class);
            return csvFileList.size();
        } catch (NoSuchFileException exception) {
            throw new CensusException(exceptionType.CENSUS_FILE_ERROR, "please enter proper file path or file type");
        } catch (RuntimeException exception) {
            throw new CensusException(exceptionType.OTHER_FILE_ERROR, "please enter proper delimeter or proper header");
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (CSVBuilderException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public String getStateWiseSortedCensusData() throws CensusException{
        if (csvFileList == null || csvFileList.size() == 0){
            throw new CensusException(exceptionType.NO_CENSUS_DATA, "No Census Data");
        }
        Comparator<StateCensus> censusComparator = Comparator.comparing(census -> census.getStateName());
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(csvFileList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<StateCensus> censusComparator) {
        for (int i=0; i< csvFileList.size()-1; i++) {
            for (int j = 0; j < csvFileList.size() - i - 1; j++) {
                StateCensus census1 = csvFileList.get(j);
                StateCensus census2 = csvFileList.get(j+1);
                if (censusComparator.compare(census1, census2) > 0){
                    csvFileList.set(j, census2);
                    csvFileList.set(j+1, census1);
                }
            }

        }
    }
}




