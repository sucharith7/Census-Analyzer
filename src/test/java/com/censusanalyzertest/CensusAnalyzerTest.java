package com.censusanalyzertest;

import com.censusanalyzer.CensusAnalyzer;
import org.junit.Assert;
import org.junit.Test;

public class CensusAnalyzerTest {

    public final String CSV_FILE = "C:\\Users\\sucha\\IdeaProjects\\CensusAnalyzer\\StateCensusData.csv";

    @Test
    public void givenCSVFile_WhenMatchNumberOfRecords_ShouldReturnNumber() {
        CensusAnalyzer censusAnalyzer = new CensusAnalyzer();
        int numberOfStates = censusAnalyzer.loadCensusData(CSV_FILE);
        Assert.assertEquals(29,numberOfStates);
    }
}



