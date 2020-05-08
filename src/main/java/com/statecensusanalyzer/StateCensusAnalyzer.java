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
    List<CensusData> csvStateList = null;

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
        Comparator<StateCensus> StateCensusComparator = Comparator.comparing(census -> census.getStateName());
        this.sort(StateCensusComparator,csvFileList);
        String sortedStateCensusJson = new Gson().toJson(csvFileList);
        return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedCensusData() throws CensusException{
        if (csvStateList == null || csvStateList.size() == 0){
            throw new CensusException(exceptionType.NO_CENSUS_DATA, "No Census Data");
        }
        Comparator<CensusData> CensusDataComparator = Comparator.comparing(census -> census.getStateName());
        this.sort(CensusDataComparator,csvStateList);
        String sortedCensusDataJson = new Gson().toJson(csvStateList);
        return sortedCensusDataJson;
    }

    private <E> void sort(Comparator<E> censuscomparator,List<E> censusList) {
        for (int index1=0; index1 < censusList.size() -1; index1++) {
            for (int index2 = 0; index2 < censusList.size() - index1 - 1; index2++) {
                E census1 = (E) censusList.get(index2);
                E census2 = (E) censusList.get(index2+1);
                if (censuscomparator.compare(census1, census2) > 0){
                    censusList.set(index2, census2);
                    censusList.set(index2+1, census1);
                }
            }

        }
    }
}




