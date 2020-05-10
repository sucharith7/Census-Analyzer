package com.statecensusanalyzer;

import com.statecensus.StateCensus;

public class StateCensusDAO {
    public String state;
    public String stateCode;
    public int densityPerSqKm;
    public int areaInSqKm;
    public int population;

    public StateCensusDAO(StateCensus stateCensus) {
        state = stateCensus.state;
        densityPerSqKm = stateCensus.densityPerSqKm;
        areaInSqKm = stateCensus.areaInSqKm;
        population = stateCensus.population;
    }
}
