package com.censusanalyzertest;

import com.censusanalyzer.CensusAnalyzer;
import com.censusexception.CensusException;
import com.codeanalyzer.CodeAnalyzer;
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
        CensusAnalyzer censusAnalyzer = new CensusAnalyzer();
        int numberOfStates = 0;
        try {
            numberOfStates = censusAnalyzer.loadCensusData(CSV_FILE);
        } catch (CensusException exception) {
            exception.printStackTrace();
        }
        Assert.assertEquals(29,numberOfStates);
    }

    @Test
    public void givenCSVFile_WhenDoesNotExist_ShouldThrowException() {
        CensusAnalyzer censusAnalyzer = new CensusAnalyzer();
        int numberOfStates = 0;
        try {
            numberOfStates = censusAnalyzer.loadCensusData(WRONG_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenCSVFile_WhenNotCorrectType_ShouldThrowException() {
        CensusAnalyzer censusAnalyzer = new CensusAnalyzer();
        int numberOfStates = 0;
        try {
            numberOfStates = censusAnalyzer.loadCensusData(WRONG_TYPE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenCSVFile_WhenImproperDelimeter_ShouldThrowException() {
        CensusAnalyzer censusAnalyzer = new CensusAnalyzer();
        int numberOfStates = 0;
        try {
            numberOfStates = censusAnalyzer.loadCensusData(DATA_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.OTHER_FILE_ERROR,CensusException.exceptionType.OTHER_FILE_ERROR);
        }
    }

    @Test
    public void givenCSVFile_WhenImproperHeader_ShouldThrowException() {
        CensusAnalyzer censusAnalyzer = new CensusAnalyzer();
        int numberOfStates = 0;
        try {
            numberOfStates = censusAnalyzer.loadCensusData(CSV_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.OTHER_FILE_ERROR,CensusException.exceptionType.OTHER_FILE_ERROR);
        }
    }
   // 2.1
    @Test
    public void givenStateCSVFile_WhenMatchNumberOfRecords_ShouldReturnNumber() {
        CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
        int count = 0;
        try {
            count = codeAnalyzer.loadStateData(CSV_STATE);
        } catch (CensusException exception) {
            exception.printStackTrace();
        }
        Assert.assertEquals(37,count);
    }

    @Test
    public void givenStateCSVFile_WhenDoesNotExist_ShouldThrowException() {
        CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
        int count = 0;
        try {
            count = codeAnalyzer.loadStateData(WRONG_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenStateCSVFile_WhenNotCorrectType_ShouldThrowException() {
        CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
        int count = 0;
        try {
            count = codeAnalyzer.loadStateData(WRONG_TYPE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenStateCSVFile_WhenDelimeterIsWrong_ShouldThrowException() {
        CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
        int count = 0;
        try {
            count = codeAnalyzer.loadStateData(DATA_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }

    @Test
    public void givenStateCSVFile_WhenHeaderIsWrong_ShouldThrowException() {
        CodeAnalyzer codeAnalyzer = new CodeAnalyzer();
        int count = 0;
        try {
            count = codeAnalyzer.loadStateData(DATA_FILE);
        } catch (CensusException exception) {
            Assert.assertEquals(CensusException.exceptionType.CENSUS_FILE_ERROR,CensusException.exceptionType.CENSUS_FILE_ERROR);
        }
    }
}



