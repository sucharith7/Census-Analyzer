package com.censusexception;

public class CensusException extends Exception{
    public final exceptionType type ;

    public enum exceptionType{
        CENSUS_FILE_ERROR
    };

    public CensusException(exceptionType type, String message) {
        super(message);
        this.type = type;
    }
}

