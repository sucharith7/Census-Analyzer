package com.censusanalyzertest;

import com.censusanalyzer.CensusAnalyzer;
import com.censusexception.CensusException;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyzerTest {

    public final String CSV_FILE = "C:\\Users\\sucha\\IdeaProjects\\CensusAnalyzer\\StateCensusData.csv";
    public final String WRONG_FILE= "C:\\Users\\sucha\\IdeaProjects\\CensusAnalyzer\\StateCensu.csv";

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
}



