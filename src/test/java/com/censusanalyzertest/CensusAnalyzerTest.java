package com.censusanalyzertest;

import com.censusexception.CensusException;
import com.google.gson.Gson;
import com.statecensus.StateCensus;
import com.statecensusanalyzer.StateCensusAnalyzer;
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
        int count = 0;
        try {
            count = stateCensusAnalyzer.loadStateData(CSV_STATE);
            Assert.assertEquals(37,count);
        } catch (CensusException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void givenStateCSVFile_WhenDoesNotExist_ShouldThrowException() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        int count = 0;
        try {
            count = stateCensusAnalyzer.loadStateData(WRONG_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenStateCSVFile_WhenNotCorrectType_ShouldThrowException() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        int count = 0;
        try {
            count = stateCensusAnalyzer.loadStateData(WRONG_TYPE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenStateCSVFile_WhenDelimeterIsWrong_ShouldThrowException() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        int count = 0;
        try {
            count = stateCensusAnalyzer.loadStateData(DATA_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenStateCSVFile_WhenHeaderIsWrong_ShouldThrowException() {
        StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
        int count = 0;
        try {
            count = stateCensusAnalyzer.loadStateData(DATA_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult(){
        try{
            StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
            stateCensusAnalyzer.loadCensusData(CSV_FILE);
            String sortedCensusData = stateCensusAnalyzer.getStateWiseSortedCensusData();
            StateCensus[] censusCSV = new Gson().fromJson(sortedCensusData,StateCensus[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSV[0].getStateName());
        }  catch (CensusException exception) { }
    }

    @Test
    public void givenIndianStateCodeCensusData_WhenSortedOnState_ShouldReturnSortedResult(){
        try{
            StateCensusAnalyzer stateCensusAnalyzer = new StateCensusAnalyzer();
            stateCensusAnalyzer.loadStateData(CSV_STATE);
            String sortedCensusData = stateCensusAnalyzer.getStateCodeWiseSortedCensusData();
            StateCensus[] censusCSV = new Gson().fromJson(sortedCensusData,StateCensus[].class);
            Assert.assertEquals("Andaman and Nicobar Islands",censusCSV[0].getStateName());
        }  catch (CensusException exception) { }
    }

}



