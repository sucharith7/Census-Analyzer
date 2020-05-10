package com.censusanalyzertest;

import com.censusexception.CensusException;
import com.google.gson.Gson;
import com.statecensusanalyzer.StateCensusAnalyzer;
import com.statecensusanalyzer.StateCensusDAO;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyzerTest {

    public final String CSV_FILE = "C:\\Users\\sucha\\IdeaProjects\\CensusAnalyzer\\StateCensusData.csv";
    public final String WRONG_FILE= "C:\\Users\\sucha\\IdeaProjects\\CensusAnalyzer\\StateCensu.csv";
    public final String WRONG_TYPE = "C:\\Users\\sucha\\IdeaProjects\\CensusAnalyzer\\StateCensusData.txt";
    public final String DATA_FILE= "C:\\Users\\sucha\\IdeaProjects\\CensusAnalyzer\\statecensus.csv";
    public final String CSV_STATE = "C:\\Users\\sucha\\IdeaProjects\\CensusAnalyzer\\StateCode.csv";

    @Test
    public void givenCSVFile_WhenMatchNumberOfRecords_ShouldReturnNumber() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        int numberOfStates = 0;
        try {
            numberOfStates = stateCensusAnalyzer.loadCensusData(CSV_FILE);
            Assert.assertEquals(29,numberOfStates);
        } catch (CensusException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void givenCSVFile_WhenDoesNotExist_ShouldThrowException() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        int numberOfStates = 0;
        try {
            numberOfStates = stateCensusAnalyzer.loadCensusData(WRONG_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenCSVFile_WhenNotCorrectType_ShouldThrowException() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        int numberOfStates = 0;
        try {
            numberOfStates = stateCensusAnalyzer.loadCensusData(WRONG_TYPE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenCSVFile_WhenImproperDelimeter_ShouldThrowException() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        int numberOfStates = 0;
        try {
            numberOfStates = stateCensusAnalyzer.loadCensusData(DATA_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.OTHER_FILE_ERROR,CensusException.exceptionType.OTHER_FILE_ERROR);
        }
    }

    @Test
    public void givenCSVFile_WhenImproperHeader_ShouldThrowException() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        int numberOfStates = 0;
        try {
            numberOfStates = stateCensusAnalyzer.loadCensusData(CSV_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.OTHER_FILE_ERROR,CensusException.exceptionType.OTHER_FILE_ERROR);
        }
    }
   // 2.1
    @Test
    public void givenStateCSVFile_WhenMatchNumberOfRecords_ShouldReturnNumber() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        try {
            stateCensusAnalyzer.loadCensusData(CSV_FILE);
            int count = stateCensusAnalyzer.loadStateData(CSV_STATE);
            Assert.assertEquals(29,count);
        } catch (CensusException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void givenStateCSVFile_WhenDoesNotExist_ShouldThrowException() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        try {
            int count = stateCensusAnalyzer.loadStateData(WRONG_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenStateCSVFile_WhenNotCorrectType_ShouldThrowException() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        try {
            int count = stateCensusAnalyzer.loadStateData(WRONG_TYPE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenStateCSVFile_WhenDelimeterIsWrong_ShouldThrowException() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        try {
           int count = stateCensusAnalyzer.loadStateData(DATA_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenStateCSVFile_WhenHeaderIsWrong_ShouldThrowException() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        try {
           int count = stateCensusAnalyzer.loadStateData(DATA_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult(){
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        try{
            stateCensusAnalyzer.loadCensusData(CSV_FILE);
            String sortedCensusData = stateCensusAnalyzer.getStateWiseSortedCensusData();
            StateCensusDAO[] stateCensus = new Gson().fromJson(sortedCensusData,StateCensusDAO[].class);
            Assert.assertEquals("Andhra Pradesh",stateCensus[0].state);
        }  catch (CensusException exception) { }
    }

    @Test
    public void givenIndianStateCodeCensusData_WhenSortedOnState_ShouldReturnSortedResult(){
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        try{
            stateCensusAnalyzer.loadStateData(CSV_STATE);
            String sortedCensusData = stateCensusAnalyzer.getStateCodeWiseSortedCensusData();
            StateCensusDAO[] censusData = new Gson().fromJson(sortedCensusData, StateCensusDAO[].class);
            Assert.assertEquals("AP", censusData[0].stateCode);
        }  catch (CensusException exception) { }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnStatePopulation_ShouldReturnMostPopulous(){
        try {
            StateCensusAnalyzer statecensusAnalyzer = new StateCensusAnalyzer();
            statecensusAnalyzer.loadCensusData(CSV_FILE);
            String mostPopulous = statecensusAnalyzer.getStateWiseMostPopulousState();
            StateCensusDAO censusCSV[] = new Gson().fromJson(String.valueOf(mostPopulous), StateCensusDAO[].class);
            Assert.assertEquals(199812341,censusCSV[censusCSV.length-1].population);
        } catch (CensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnDensity_ShouldReturnMostPopulationDensity() {
        try {
            StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
            stateCensusAnalyzer.loadCensusData(CSV_FILE);
            String mostPopulous = stateCensusAnalyzer.getStateWiseMostPopulationDensityState();
            StateCensusDAO censusCSV[] = new Gson().fromJson(mostPopulous, StateCensusDAO[].class);
            Assert.assertEquals(1102, censusCSV[censusCSV.length - 1].densityPerSqKm);
            Assert.assertEquals("Bihar",censusCSV[censusCSV.length - 1].state);
        } catch (CensusException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedByArea_ShouldReturnLargestAndSmallestState(){
        try {
            StateCensusAnalyzer statecensusAnalyzer = new StateCensusAnalyzer();
            statecensusAnalyzer.loadCensusData(CSV_FILE);
            String mostPopulous = statecensusAnalyzer.getStateWiseLargestAndSmallestState();
            StateCensusDAO censusCSV[] = new Gson().fromJson(mostPopulous, StateCensusDAO[].class);
            Assert.assertEquals(342239, censusCSV[censusCSV.length - 1].areaInSqKm);
            Assert.assertEquals("Rajasthan",censusCSV[censusCSV.length - 1].state);
            Assert.assertEquals(3702, censusCSV[censusCSV.length - 28].areaInSqKm);
            Assert.assertEquals("Goa", censusCSV[censusCSV.length - 28].state);
        } catch (CensusException e) {
            e.printStackTrace();
        }
    }
}



