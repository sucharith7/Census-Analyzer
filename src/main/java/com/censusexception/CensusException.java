package com.censusexception;

public class CensusException extends Exception{

    public CensusException(String message, String name) {
        super(message);
        this.type = exceptionType.valueOf(name);
    }

    public enum exceptionType{
        CENSUS_FILE_ERROR,OTHER_FILE_ERROR;
    }

    public exceptionType type;

    public CensusException(exceptionType type, String message,RuntimeException exception){
        super(message);
        this.type = type;
    }

    public CensusException(exceptionType type, String cause){
        super(cause);
        this.type = type;
    }

    public CensusException(exceptionType type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}

