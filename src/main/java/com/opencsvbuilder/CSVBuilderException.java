package com.opencsvbuilder;

import com.censusexception.CensusException;

public class CSVBuilderException extends Exception{

    enum exceptionType{
        CENSUS_FILE_ERROR,OTHER_FILE_ERROR
    }
    CensusException.exceptionType type;
    public CSVBuilderException(String message,CensusException.exceptionType type) {
        super(message);
        this.type = type;
    }

    public CSVBuilderException(String message,CensusException.exceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
