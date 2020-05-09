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
import java.util.*;
import java.util.stream.StreamSupport;

public class StateCensusAnalyzer {
    Map<String, StateCensus> csvStateCensusMap = null;
    Map<String, CensusData> csvCensusDataMap = null;

    public StateCensusAnalyzer() {
        csvStateCensusMap = new HashMap<>();
        csvCensusDataMap = new HashMap<>();
    }

    public int loadCensusData(String csvFilePath) throws CensusException {

        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<StateCensus> iteratorStateCensus = csvBuilder.getCSVFileIterator(reader,StateCensus.class);
            Iterable<StateCensus> StateCensusIterable = () -> iteratorStateCensus;
            StreamSupport.stream(StateCensusIterable.spliterator(),false)
                    .forEach(censusCsv -> csvStateCensusMap.put(censusCsv.getStateName(), censusCsv));
        } catch (NoSuchFileException exception) {
            throw new CensusException(CensusException.exceptionType.CENSUS_FILE_ERROR, "please enter proper file path or file type");
        } catch (RuntimeException exception) {
            throw new CensusException(CensusException.exceptionType.OTHER_FILE_ERROR, "please enter proper delimter  or proper header");
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (CSVBuilderException exception) {
            exception.printStackTrace();
        }
        return csvStateCensusMap.size();
    }


    public int loadStateData(String csvFileState) throws CensusException {

        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFileState));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CensusData> iteratorCensusData = csvBuilder.getCSVFileIterator(reader,CensusData.class);
            Iterable<CensusData> CensusDataIterable = () -> iteratorCensusData;
            StreamSupport.stream(CensusDataIterable.spliterator(),false)
                    .forEach(censusCsv -> csvCensusDataMap.put(censusCsv.getStateName(), censusCsv));
        } catch (NoSuchFileException exception) {
            throw new CensusException(exceptionType.CENSUS_FILE_ERROR, "please enter proper file path or file type");
        } catch (RuntimeException exception) {
            throw new CensusException(exceptionType.OTHER_FILE_ERROR, "please enter proper delimeter or proper header");
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (CSVBuilderException exception) {
            exception.printStackTrace();
        }
        return csvCensusDataMap.size();
    }

    public String getStateWiseSortedCensusData() throws CensusException{
        if (csvStateCensusMap == null || csvStateCensusMap.size() == 0){
            throw new CensusException(CensusException.exceptionType.NO_CENSUS_DATA, "No Census Data");
        }
        Comparator<StateCensus> censusComparator = Comparator.comparing(census -> census.getStateName());
        List<StateCensus> sortedList = this.sort(censusComparator, new ArrayList<>(csvStateCensusMap.values()));
        String sortedStateCensusJson = new Gson().toJson(sortedList);
        return sortedStateCensusJson;
    }

    public String getStateCodeWiseSortedCensusData() throws CensusException{
        if (csvCensusDataMap == null || csvCensusDataMap.size() == 0){
            throw new CensusException(CensusException.exceptionType.NO_CENSUS_DATA, "No Census Data");
        }
        Comparator<CensusData> censusComparator = Comparator.comparing(census -> census.getStateName());
        List<CensusData> sortedList = this.sort(censusComparator, new ArrayList<>(csvCensusDataMap.values()));
        String sortedCensusDataJson = new Gson().toJson(sortedList);
        return sortedCensusDataJson;
    }

    private <E> List<E> sort(Comparator<E> comparator, List<E> censusList) {
        for (int index1=0; index1 < censusList.size() -1; index1++) {
            for (int index2 = 0; index2 < censusList.size() - index1 - 1; index2++) {
                E census1 = (E) censusList.get(index2);
                E census2 = (E) censusList.get(index2+1);
                if (comparator.compare(census1, census2) > 0){
                    censusList.set(index2, census2);
                    censusList.set(index2+1, census1);
                }
            }

        }
        return censusList;
    }
}




