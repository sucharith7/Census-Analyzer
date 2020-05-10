package com.statecensusanalyzer;

import com.census.ICSVBuilder;
import com.censusdata.CensusData;
import com.censusexception.CensusException;
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
    Map<String,StateCensusDAO> csvStateCensusMap = new HashMap<String, StateCensusDAO>();

    public int loadCensusData(String csvFilePath) throws CensusException {
        return this.loadStateCodeData(csvFilePath, StateCensus.class);
    }

    public int loadStateData(String csvFilePath) throws CensusException {
        return this.loadStateCodeData(csvFilePath,CensusData.class);
    }

    public <E> int loadStateCodeData(String csvFilePath, Class<E> csvClass) throws CensusException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> iteratorCSVCensus = csvBuilder.getCSVFileIterator(reader, csvClass);
            Iterable<E> csvStateCensusIterable = () -> iteratorCSVCensus;
            if (csvClass.getName().equals("com.statecensus.StateCensus")) {
                StreamSupport.stream(csvStateCensusIterable.spliterator(), false)
                        .map(StateCensus.class::cast)
                        .forEach(censusCSV -> csvStateCensusMap.put(censusCSV.state,new StateCensusDAO(censusCSV)));
                return csvStateCensusMap.size();
            }
            if (csvClass.getName().equals("com.censusdata.CensusData")) {
                StreamSupport.stream(csvStateCensusIterable.spliterator(), false)
                        .map(CensusData.class::cast)
                        .filter(censusCSV -> csvStateCensusMap.get(censusCSV.getStateName()) != null)
                        .forEach(censusCSV -> csvStateCensusMap.get(censusCSV.getStateName()).stateCode = censusCSV.getStateCode());
                return csvStateCensusMap.size();
            }

            } catch(NoSuchFileException exception){
                throw new CensusException(CensusException.exceptionType.CENSUS_FILE_ERROR, "please enter proper file path or file type");
            } catch(RuntimeException exception){
                throw new CensusException(CensusException.exceptionType.OTHER_FILE_ERROR, "please enter proper delimter  or proper header");
            } catch(IOException exception){
                exception.printStackTrace();
            } catch(CSVBuilderException exception){
                exception.printStackTrace();
            }
            return 0;
        }

        public String getStateWiseSortedCensusData() throws CensusException {
            if (csvStateCensusMap == null || csvStateCensusMap.size() == 0)
                throw new CensusException(CensusException.exceptionType.NO_CENSUS_DATA, "No Census Data");
            Comparator<StateCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
            List<StateCensusDAO> sortedList = this.sort(censusComparator, new ArrayList<>(csvStateCensusMap.values()));
            return new Gson().toJson(sortedList);
        }

        public String getStateCodeWiseSortedCensusData () throws CensusException {
            if (csvStateCensusMap == null || csvStateCensusMap.size() == 0)
                throw new CensusException(CensusException.exceptionType.NO_CENSUS_DATA, "No Census Data");
            Comparator<StateCensusDAO> codeComparator = Comparator.comparing(census -> census.stateCode);
            List<StateCensusDAO> sortedList = this.sort(codeComparator, new ArrayList<>(csvStateCensusMap.values()));
            return new Gson().toJson(sortedList);
        }

    public String getStateWiseMostPopulousState() throws CensusException {
        if(csvStateCensusMap == null || csvStateCensusMap.size() == 0){
            throw new CensusException(CensusException.exceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        Comparator<StateCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        List<StateCensusDAO> sortedStateCode = this.sort(censusComparator, new ArrayList<>(csvStateCensusMap.values()));
        String sortedStateCodeJson = new Gson().toJson(sortedStateCode);
        return sortedStateCodeJson;
    }

    public String getStateWiseMostPopulationDensityState() throws CensusException {
        if(csvStateCensusMap == null || csvStateCensusMap.size() == 0){
            throw new CensusException(CensusException.exceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        Comparator<StateCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqKm);
        List<StateCensusDAO> sortedStateCode = this.sort(censusComparator, new ArrayList<>(csvStateCensusMap.values()));
        String sortedStateCodeJson = new Gson().toJson(sortedStateCode);
        return sortedStateCodeJson;
    }

    public String getStateWiseLargestAndSmallestState() throws CensusException {
        if(csvStateCensusMap == null || csvStateCensusMap.size() == 0){
            throw new CensusException(CensusException.exceptionType.NO_CENSUS_DATA,"No Census Data");
        }
        Comparator<StateCensusDAO> censusComparator = Comparator.comparing(census -> census.areaInSqKm);
        List<StateCensusDAO> sortedStateCode = this.sort(censusComparator, new ArrayList<>(csvStateCensusMap.values()));
        String sortedStateCodeJson = new Gson().toJson(sortedStateCode);
        return sortedStateCodeJson;
    }

        private <E > List < E > sort(Comparator < E > comparator, List < E > censusList) {
            for (int index1 = 0; index1 < censusList.size() - 1; index1++) {
                for (int index2 = 0; index2 < censusList.size() - index1 - 1; index2++) {
                    E census1 = (E) censusList.get(index2);
                    E census2 = (E) censusList.get(index2 + 1);
                    if (comparator.compare(census1, census2) > 0) {
                        censusList.set(index2, census2);
                        censusList.set(index2 + 1, census1);
                    }
                }

            }
            return censusList;
        }
    }





