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

}



